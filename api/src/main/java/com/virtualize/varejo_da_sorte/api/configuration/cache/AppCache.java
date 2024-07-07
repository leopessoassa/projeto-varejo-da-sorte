package com.virtualize.varejo_da_sorte.api.configuration.cache;



import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AppCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppCache.class);

    /**
     * cacheManager
     */
    public static final String CACHE_MANAGER_STATIC = "CACHE_MANAGER_STATIC";
    public static final String CACHE_MANAGER_SDC = "CACHE_MANAGER_SDC";
    public static final String CACHE_MANAGER_REPOSITORY = "CACHE_MANAGER_REPOSITORY";

    /**
     * static
     */
    public static final String ESTADO_CIVIL = "ESTADO_CIVIL";
    public static final String CLASSIFICADOR_REPRESENTANTE = "CLASSIFICADOR_REPRESENTANTE";
    public static final String CONCLUSAO_PERICIA = "CONCLUSAO_PERICIA";
    public static final String DEPENDENTE_VINCULO = "DEPENDENTE_VINCULO";
    public static final String MEIO_PAGAMENTO_CREDITO = "MEIO_PAGAMENTO_CREDITO";
    public static final String MOTIVO_EXTINCAO_COTA = "MOTIVO_EXTINCAO_COTA";

    /**
     * SDC
     */
    public static final String AAP = "AAP";
    public static final String APS = "APS";
    public static final String APS_LIST = "APS_LIST";
    public static final String EMISSOR_IDENTIDADE = "EMISSOR_IDENTIDADE";
    public static final String ENDERECO_AGENCIA_BANCARIA = "ENDERECO_AGENCIA_BANCARIA";
    public static final String INSTITUICOES_BANCARIAS = "INSTITUICOES_BANCARIAS";
    public static final String UFS = "UFS";
    public static final String AGENCIAS_BANCARIAS = "AGENCIAS_BANCARIAS";
    public static final String ORGAO_PAGADOR = "ORGAO_PAGADOR";
    public static final String NOME_BANCO = "NOME_BANCO";
    public static final String UNIDADE_ORGANICA = "UNIDADE_ORGANICA";
    public static final String NACIONALIDADE = "NACIONALIDADE";


    /**
     * BD
     */
    public static final String SITUACAO_BENEFICIO = "SITUACAO_BENEFICIO";
    public static final String CAPACIDADE_LABORATIVA = "CAPACIDADE_LABORATIVA";
    public static final String DESPACHO_CONCESSAO = "DESPACHO_CONCESSAO";
    public static final String ESPECIES_BENEFICIOS = "ESPECIES_BENEFICIOS";
    public static final String ESPECIE_BENEFICIOS = "ESPECIE_BENEFICIOS";
    public static final String FORMAS_FILIACAO = "FORMAS_FILIACAO";
    public static final String FORMA_FILIACAO = "FORMA_FILIACAO";
    public static final String RAMO_ATIVIDADE = "RAMO_ATIVIDADE";
    public static final String MOTIVO_SUSPENSAO = "MOTIVO_SUSPENSAO";
    public static final String MOTIVO_CESSACAO = "MOTIVO_CESSACAO";
    public static final String ESPECIE_BENEFICIO = "ESPECIE_BENEFICIO";
    public static final String TRATAMENTO = "TRATAMENTO";
    public static final String SALARIO_MINIMO_REFERENCIA = "SALARIO_MINIMO_REFERENCIA";
    public static final String BDSISBEN_GLOBAL = "BDSISBEN_GLOBAL";





    public enum CacheKeys {

        //static
        ESTADO_CIVIL(AppCache.ESTADO_CIVIL, CacheType.STATIC),
        CLASSIFICADOR_REPRESENTANTE(AppCache.CLASSIFICADOR_REPRESENTANTE, CacheType.STATIC),
        CONCLUSAO_PERICIA(AppCache.CONCLUSAO_PERICIA, CacheType.STATIC),
        DEPENDENTE_VINCULO(AppCache.DEPENDENTE_VINCULO, CacheType.STATIC),
        MEIO_PAGAMENTO_CREDITO(AppCache.MEIO_PAGAMENTO_CREDITO, CacheType.STATIC),
        MOTIVO_EXTINCAO_COTA(AppCache.MOTIVO_EXTINCAO_COTA, CacheType.STATIC),

        //SDC
        AAP(AppCache.AAP, CacheType.SDC),
        APS(AppCache.APS, CacheType.SDC),
        APS_LIST(AppCache.APS_LIST, CacheType.SDC),
        EMISSOR_IDENTIDADE(AppCache.EMISSOR_IDENTIDADE, CacheType.SDC),
        ENDERECO_AGENCIA_BANCARIA(AppCache.ENDERECO_AGENCIA_BANCARIA, CacheType.SDC),
        INSTITUICOES_BANCARIAS(AppCache.INSTITUICOES_BANCARIAS, CacheType.SDC),
        UFS(AppCache.UFS, CacheType.SDC),
        AGENCIAS_BANCARIAS(AppCache.AGENCIAS_BANCARIAS, CacheType.SDC),
        ORGAO_PAGADOR(AppCache.ORGAO_PAGADOR, CacheType.SDC),
        NOME_BANCO(AppCache.NOME_BANCO, CacheType.SDC),
        UNIDADE_ORGANICA(AppCache.UNIDADE_ORGANICA, CacheType.SDC),
        NACIONALIDADE(AppCache.NACIONALIDADE, CacheType.SDC),

        //Repository
        CAPACIDADE_LABORATIVA(AppCache.CAPACIDADE_LABORATIVA, CacheType.REPOSITORY),
        DESPACHO_CONCESSAO(AppCache.DESPACHO_CONCESSAO, CacheType.REPOSITORY),
        ESPECIES_BENEFICIOS(AppCache.ESPECIES_BENEFICIOS, CacheType.REPOSITORY),
        ESPECIE_BENEFICIOS(AppCache.ESPECIE_BENEFICIOS, CacheType.REPOSITORY),
        RAMO_ATIVIDADE(AppCache.RAMO_ATIVIDADE, CacheType.REPOSITORY),
        MOTIVO_SUSPENSAO(AppCache.MOTIVO_SUSPENSAO, CacheType.REPOSITORY),
        MOTIVO_CESSACAO(AppCache.MOTIVO_CESSACAO, CacheType.REPOSITORY),
        FORMAS_FILIACAO(AppCache.FORMAS_FILIACAO, CacheType.REPOSITORY),
        SITUACAO_BENEFICIO(AppCache.SITUACAO_BENEFICIO, CacheType.REPOSITORY),
        FORMA_FILIACAO(AppCache.FORMA_FILIACAO, CacheType.REPOSITORY),
        ESPECIE_BENEFICIO(AppCache.ESPECIE_BENEFICIO, CacheType.REPOSITORY),
        TRATAMENTO(AppCache.TRATAMENTO, CacheType.REPOSITORY),
        SALARIO_MINIMO_REFERENCIA(AppCache.SALARIO_MINIMO_REFERENCIA, CacheType.REPOSITORY),
        BDSISBEN_GLOBAL(AppCache.BDSISBEN_GLOBAL, CacheType.REPOSITORY),
        ;


        public final String descricao;
        public final CacheType tipo;

        private CacheKeys(String descricao, CacheType tipo) {
            this.descricao = descricao;
            this.tipo = tipo;
        }

        @Override
        public String toString() {
            return this.descricao;
        }

        public static List<CacheKeys> list(CacheType type) {
            return Stream.of(CacheKeys.values())
                    .filter(c -> c.tipo.equals(type))
                    .collect(Collectors.toList());
        }

        public static List<String> listDescricao(CacheType type) {
            return Stream.of(CacheKeys.values())
                    .filter(c -> c.tipo.equals(type))
                    .map(c -> c.descricao)
                    .collect(Collectors.toList());
        }

    }

    public enum CacheType {
        STATIC, REPOSITORY, SDC;
    }


    public static void main_(String[] args) {
        LOGGER.debug(">>>>>>>>>> STATIC <<<<<<<<<<");
        for (CacheKeys c : CacheKeys.list(CacheType.STATIC)) {
            LOGGER.info(c.toString());
        }
        LOGGER.debug(">>>>>>>>>> DYNAMIC <<<<<<<<<<");
        for (CacheKeys c : CacheKeys.list(CacheType.REPOSITORY)) {
            LOGGER.info(c.toString());
        }
        LOGGER.debug(">>>>>>>>>> SDC <<<<<<<<<<");
        for (CacheKeys c : CacheKeys.list(CacheType.SDC)) {
            LOGGER.info(c.toString());
        }
    }

}
