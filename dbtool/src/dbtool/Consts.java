/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dbtool;

/**
 *
 * @author xhwu
 */
public interface Consts {
        String[] tables = new String[]{"HY_YRIQ_F", "HY_YRP_F", "HY_YRPDDB_F", "HY_YRQ_F", "HY_YRQS_F", "HY_YRTDZ_F", "HY_YRWE_F",
            "HY_YRWEAP_F", "HY_YRWT_F", "HY_YRZ_F", "HY_ZN_A", "HY_ZNSTRL_A", "HY_ZNSW_G", "HY_ZNW_G",
            "HY_ZQDV_A", "HY_ADDVCD_J", "HY_BSCDNM_J", "HY_DAEX_I", "HY_DATBDL_I", "HY_DBFP_J", "HY_DBTP_J",
            "HY_DCCHPD_D", "HY_DCCS_D", "HY_DCP_D", "HY_DCQ_D", "HY_DCQS_D", "HY_DCS_C", "HY_DCWE_D",
            "HY_DCWEAP_D", "HY_DCWT_D", "HY_DCZ_D", "HY_DIQ_C", "HY_DMXP_F", "HY_DP_C", "HY_DQ_C", "HY_DQS_C",
            "HY_DTDQQS_B", "HY_DWE_C", "HY_DWEAP_C", "HY_DWT_C", "HY_DZ_C", "HY_ERCR_I", "HY_EVE_A",
            "HY_EVIT_A", "HY_EVP_A", "HY_FDHEEX_B", "HY_FDLFIV_G", "HY_GPH_A", "HY_HLTDZ_B", "HY_HMXP_F",
            "HY_HPCH_H", "HY_HPQR_H", "HY_HYIVRP_G", "HY_ICEX_B", "HY_IMXFW_F", "HY_IVP_G", "HY_IVSP_A",
            "HY_MMXP_F", "HY_MTCHPD_E", "HY_MTCS_E", "HY_MTIQ_E", "HY_MTP_E", "HY_MTPDDB_E", "HY_MTQ_E",
            "HY_MTQS_E", "HY_MTTDZ_E", "HY_MTWE_E", "HY_MTWEAP_E", "HY_MTWT_E", "HY_MTZ_E", "HY_OBCHPD_G",
            "HY_OBIQ_G", "HY_OBP_G", "HY_OBPDDB_G", "HY_OBQ_G", "HY_OBQS_G", "HY_OBTDQ_G", "HY_OBTDW_G",
            "HY_OBTVTDV_G", "HY_PHPBF_A", "HY_PREX_B", "HY_RC_H", "HY_RCNT_H", "HY_RMSB_J", "HY_RVENCH_A",
            "HY_RVFHEX_B", "HY_SOLUCT_J", "HY_SPENCH_A", "HY_STBMDV_A", "HY_STSC_A", "HY_STSW_G", "HY_STW_G",
            "HY_STXSRL_A", "HY_SWENCH_A", "HY_TC_G", "HY_TMDFDA_G", "HY_UNAD_I", "HY_WFDZ_F", "HY_WSCH_H",
            "HY_WSFHEX_B", "HY_WSOBTDW_G", "HY_WSQR_H", "HY_XSMSRS_G", "HY_XSPAQT_G", "HY_YRCHPD_F",
            "HY_YRCS_F", "HY_YRICCO_F"};
        String[] stcd = new String[]{
            "10000001","10000002"
        };
        String[] stname = new String[]{
            "南坪","南坪2"
        };
        String[] jdbcclass=new String[]{"--选择数据库类型--","Microsoft SQL Server","Sybase",
        "Oracle","ODBC数据源"};

         String[] SLS_tables = new String[]{"HY_DZ_C","HY_DQ_C","HY_DCS_C","HY_DQS_C",
                                            "HY_DCZ_D","HY_DCQ_D","HY_DCCS_D","HY_DCQS_D",
                                            "HY_MTZ_E","HY_MTQ_E","HY_MTCS_E","HY_MTQS_E",
                                            "HY_YRZ_F","HY_YRQ_F","HY_YRCS_F","HY_YRQS_F",};
         //日平均水位表、日平均流量表、日平均含沙量表、日平均输沙率表、
         //旬平均水位表、旬平均流量表、旬平均含沙量表、旬平均输沙率表、
         //月水位表、月流量表、月含沙量表、月输沙率表、
         //年水位表、年流量表、年含沙量表、年输沙率表
         String[] JS_tables = new String[]{"HY_DP_C","HY_DCP_D","HY_MTP_E","HY_YRP_F"};
         //日降水量表、旬降水表、月降水量表、年降水量表
         String[] SW_tables = new String[]{"HY_DWT_C","HY_DCWT_D","HY_MTWT_E","HY_YRWT_F"};
         //日水温表、旬平均水温表、月水温表、年水温表
         String[] ZF_tables = new String[]{"HY_DWE_C","HY_DWEAP_C","HY_DCWE_D","HY_DCWEAP_D",
                                            "HY_MTWE_E","HY_MTWEAP_E","HY_YRWE_F"//,"HY_YRWEAP_F"
         };
         //日水面蒸发量表、日水面蒸发量辅助表、旬水面蒸发量表、旬水面蒸发量辅助表
         //月水面蒸发量表、月水面蒸发量辅助表、年水面蒸发量表、年水面蒸发量辅助表
         String[] BL_tables = new String[]{"HY_DIQ_C","HY_MTIQ_E","HY_YRICCO_F"//,"HY_YRIQ_F",
         };
         //日平均冰流量表、月冰流量表、年冰情表、年冰流量表
         String[] CW_tables = new String[]{"HY_HLTDZ_B","HY_MTTDZ_E","HY_YRTDZ_F"};
         //逐潮高低潮位表、月潮位表、年潮位表


}
