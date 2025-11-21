package br.com.caixa.caixaverso.service;

import br.com.caixa.caixaverso.domain.dto.PerfilRiscoDTO;
import br.com.caixa.caixaverso.domain.entity.Cliente;
import br.com.caixa.caixaverso.repository.InvestimentoHistoricoRepository;
import br.com.caixa.caixaverso.repository.ClienteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.util.Locale;

@ApplicationScoped
public class PerfilRiscoFacade {

    private static final Logger LOG = Logger.getLogger(PerfilRiscoFacade.class);

    @Inject
    InvestimentoHistoricoRepository historicoRepo;

    @Inject
    ClienteRepository clienteRepository;



    @ConfigProperty(name = "perfil.peso.volume", defaultValue = "0.30")
    double W_VOLUME;

    @ConfigProperty(name = "perfil.peso.freq", defaultValue = "0.20")
    double W_FREQ;

    @ConfigProperty(name = "perfil.peso.rent", defaultValue = "0.50")
    double W_RENT;

    @ConfigProperty(name = "perfil.maxRentExpected", defaultValue = "0.12")
    double MAX_RENT_EXPECTED;

    @ConfigProperty(name = "perfil.logScale.volume", defaultValue = "15.0")
    double LOG_SCALE_VOLUME_MULT;

    @ConfigProperty(name = "perfil.logScale.freq", defaultValue = "20.0")
    double LOG_SCALE_FREQ_MULT;

    private static final int CUT_CONSERVADOR = 40;
    private static final int CUT_MODERADO = 70;

    public PerfilRiscoDTO calcularPerfil(Long clienteId) {
        if (clienteId == null) throw new IllegalArgumentException("clienteId é obrigatório");

        long ops = historicoRepo.countOpsAll(clienteId);
        double totalVolume = historicoRepo.sumVolumeAll(clienteId);
        double avgRent = historicoRepo.avgRentabilidadeAll(clienteId);

        if (ops == 0 && totalVolume == 0.0) {
            Cliente c = clienteRepository.findByIdOrNull(clienteId);
            if (c != null && c.pontuacao != null) {
                int pont = Math.max(0, Math.min(100, c.pontuacao));
                String perfil = mapScoreToPerfil(pont);
                return buildDTO(clienteId, perfil, pont);
            } else {
                return buildDTO(clienteId, "CONSERVADOR", 20);
            }
        }

        double normFreq = normalizeFrequency(ops);
        double normVol  = normalizeVolume(totalVolume);
        double normRent = normalizeRent(avgRent);

        double score = W_VOLUME * normVol + W_FREQ * normFreq + W_RENT * normRent;
        int rounded = (int) Math.round(score);
        String perfil = mapScoreToPerfil(rounded);

        LOG.infof("PerfilRisco(%d) -> ops=%d, totalVolume=%.2f, avgRent=%.4f, normF=%.2f, normV=%.2f, normR=%.2f, score=%.2f -> %s",
                clienteId, ops, totalVolume, avgRent, normFreq, normVol, normRent, score, perfil);

        return buildDTO(clienteId, perfil, rounded);
    }

    private PerfilRiscoDTO buildDTO(Long clienteId, String perfil, int pontuacao) {
        PerfilRiscoDTO dto = new PerfilRiscoDTO();
        dto.setClienteId(clienteId);
        dto.setPerfil(perfil);
        dto.setPontuacao(pontuacao);
        dto.setDescricao(defaultDescription(perfil));
        return dto;
    }

    private String defaultDescription(String perfil) {
        return switch (perfil.toUpperCase(Locale.ROOT)) {
            case "CONSERVADOR" -> "Baixa propensão ao risco; foco em segurança e liquidez.";
            case "AGRESSIVO" -> "Busca alta rentabilidade; aceita maior volatilidade.";
            default -> "Perfil equilibrado entre segurança e rentabilidade.";
        };
    }

    private double normalizeFrequency(long ops) {
        double raw = Math.log10(1.0 + ops);
        return Math.min(100.0, LOG_SCALE_FREQ_MULT * raw);
    }

    private double normalizeVolume(double volume) {
        double raw = Math.log10(1.0 + Math.max(0.0, volume));
        return Math.min(100.0, LOG_SCALE_VOLUME_MULT * raw);
    }

    private double normalizeRent(double avgRent) {
        if (avgRent <= 0.0) return 0.0;
        double ratio = avgRent / MAX_RENT_EXPECTED;
        return Math.min(100.0, ratio * 100.0);
    }

    private String mapScoreToPerfil(int score) {
        if (score < CUT_CONSERVADOR) return "CONSERVADOR";
        if (score < CUT_MODERADO) return "MODERADO";
        return "AGRESSIVO";
    }
}
