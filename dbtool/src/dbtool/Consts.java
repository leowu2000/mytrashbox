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
            "��ƺ","��ƺ2"
        };
        String[] jdbcclass=new String[]{"--ѡ�����ݿ�����--","Microsoft SQL Server","Sybase",
        "Oracle","ODBC����Դ"};

         String[] SLS_tables = new String[]{"HY_DZ_C","HY_DQ_C","HY_DCS_C","HY_DQS_C",
                                            "HY_DCZ_D","HY_DCQ_D","HY_DCCS_D","HY_DCQS_D",
                                            "HY_MTZ_E","HY_MTQ_E","HY_MTCS_E","HY_MTQS_E",
                                            "HY_YRZ_F","HY_YRQ_F","HY_YRCS_F","HY_YRQS_F",};
         //��ƽ��ˮλ����ƽ����������ƽ����ɳ������ƽ����ɳ�ʱ�
         //Ѯƽ��ˮλ��Ѯƽ��������Ѯƽ����ɳ����Ѯƽ����ɳ�ʱ�
         //��ˮλ�����������º�ɳ��������ɳ�ʱ�
         //��ˮλ�����������꺬ɳ��������ɳ�ʱ�
         String[] JS_tables = new String[]{"HY_DP_C","HY_DCP_D","HY_MTP_E","HY_YRP_F"};
         //�ս�ˮ����Ѯ��ˮ���½�ˮ�����꽵ˮ����
         String[] SW_tables = new String[]{"HY_DWT_C","HY_DCWT_D","HY_MTWT_E","HY_YRWT_F"};
         //��ˮ�±�Ѯƽ��ˮ�±���ˮ�±���ˮ�±�
         String[] ZF_tables = new String[]{"HY_DWE_C","HY_DWEAP_C","HY_DCWE_D","HY_DCWEAP_D",
                                            "HY_MTWE_E","HY_MTWEAP_E","HY_YRWE_F"//,"HY_YRWEAP_F"
         };
         //��ˮ������������ˮ��������������Ѯˮ����������Ѯˮ��������������
         //��ˮ������������ˮ����������������ˮ������������ˮ��������������
         String[] BL_tables = new String[]{"HY_DIQ_C","HY_MTIQ_E","HY_YRICCO_F"//,"HY_YRIQ_F",
         };
         //��ƽ�����������±����������������������
         String[] CW_tables = new String[]{"HY_HLTDZ_B","HY_MTTDZ_E","HY_YRTDZ_F"};
         //�𳱸ߵͳ�λ���³�λ���곱λ��


}
