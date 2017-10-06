# jreportDemo



import *SimpleJdbcCall;

public interface StoredProcedureConfig {	
	DevSimpleJdbcCall brokerAccountSearch();
	DevSimpleJdbcCall countBrokerAccountSearchResults();
	DevSimpleJdbcCall brokerAsiDataSearch();
	DevSimpleJdbcCall countBrokerAsiDataSearchResults();
	DevSimpleJdbcCall gcAccountDataSearch();
	DevSimpleJdbcCall countGcAccountDataSearch();
	DevSimpleJdbcCall brokerAccountThroughIMListSearch();
	DevSimpleJdbcCall countBrokerAccountThroughIMListSearchResults();
	DevSimpleJdbcCall reportSearch();
	DevSimpleJdbcCall modelSiList();
	DevSimpleJdbcCall countModelSiList();
	DevSimpleJdbcCall accountSiList();
	DevSimpleJdbcCall countAccountSiList();
	DevSimpleJdbcCall getSiByBusinessKeys();
	DevSimpleJdbcCall siListSearch();
	DevSimpleJdbcCall countsiListSearchResults();
	DevSimpleJdbcCall brokerMultiAccountSearch();
	DevSimpleJdbcCall brokerMultiBiaSearch();	
	/*R3 | B-11825	*/
	DevSimpleJdbcCall imAsiDataSearch();
	/*R3 | B-22866	*/
	DevSimpleJdbcCall msg48AsiDataSearch();
	
	DevSimpleJdbcCall partialAccessCodeAsiDataSearch();
	
}
-------------------------

package com.Dev.alert.config;

import oracle.jdbc.OracleTypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

import com.Dev.alert.common.config.DataSourceConfig;
import com.Dev.alert.view.data.jdbc.AccountBIADetailsMapper;
import com.Dev.alert.view.data.jdbc.AccountRefIdMapper;
import com.Dev.alert.view.data.jdbc.BIAXrefSearchResultMapper;
import com.Dev.alert.view.data.jdbc.BrokerAcctSISearchResultMapper;
import com.Dev.alert.view.data.jdbc.GCAccountSearchResultMapper;
import com.Dev.alert.view.data.jdbc.SIDetailsRowMapper;
import com.Dev.alert.view.util.DevSimpleJdbcCall;

/**
 * This configuration pre-compiles all the SimpleJdbcCall objects 
 * for calling stored procedures.
 */
@Configuration
public class OracleStoredProcedureConfig implements StoredProcedureConfig {

	@Autowired
	private DataSourceConfig dataSourceConfig;
	
	@Override @Bean
	public DevSimpleJdbcCall brokerAccountSearch() {
		DevSimpleJdbcCall call = new DevSimpleJdbcCall(dataSourceConfig.alertReadDataSource());
		//FIXME externalize these strings to StoredProcMetaData
		call.withCatalogName("PKG_ALERT")
			 .withProcedureName("BRK_ACCT_ACCESS")
			 .declareParameters(new SqlParameter("P_BROKER_ORG_ID", OracleTypes.NUMBER),
					 new SqlParameter("P_IM_ORG_ID", OracleTypes.NUMBER),
					 new SqlParameter("P_ACCESS_CODE", OracleTypes.VARCHAR),
					 new SqlParameter("P_COUNTRY", OracleTypes.VARCHAR),
					 new SqlParameter("P_SECURITY", OracleTypes.VARCHAR),
					 new SqlParameter("P_METHOD", OracleTypes.VARCHAR),
					 new SqlParameter("P_XREF", OracleTypes.VARCHAR),
					 new SqlParameter("P_PAGE_SIZE", OracleTypes.NUMBER),
					 new SqlParameter("P_PAGE_NUMBER", OracleTypes.NUMBER),
					 new SqlParameter("P_SORT_COL", OracleTypes.VARCHAR),
					 new SqlParameter("P_SORT_ORDER", OracleTypes.VARCHAR),
					 new SqlOutParameter("P_RESULT", OracleTypes.CURSOR, new BIAXrefSearchResultMapper()))
			 .compile();
		return call;
	}

	@Override
	public DevSimpleJdbcCall countBrokerAccountSearchResults() {
		DevSimpleJdbcCall call = new DevSimpleJdbcCall(dataSourceConfig.alertReadDataSource());
		//FIXME externalize these strings to StoredProcMetaData
		call.withCatalogName("PKG_ALERT")
			.withProcedureName("BRK_ACCT_ACCESS_COUNT")
			 .declareParameters(new SqlParameter("P_BROKER_ORG_ID", OracleTypes.NUMBER),
					 new SqlParameter("P_IM_ORG_ID", OracleTypes.NUMBER),
					 new SqlParameter("P_ACCESS_CODE", OracleTypes.VARCHAR),
					 new SqlParameter("P_COUNTRY", OracleTypes.VARCHAR),
					 new SqlParameter("P_SECURITY", OracleTypes.VARCHAR),
					 new SqlParameter("P_METHOD", OracleTypes.VARCHAR),
					 new SqlParameter("P_XREF", OracleTypes.VARCHAR),
					 new SqlOutParameter("P_ROW_COUNT", OracleTypes.NUMBER))
			 .compile();
		return call;
	}
	
	
	
	@Override @Bean
	public DevSimpleJdbcCall brokerAccountThroughIMListSearch() {
		DevSimpleJdbcCall call = new DevSimpleJdbcCall(dataSourceConfig.alertReadDataSource());
		//FIXME externalize these strings to StoredProcMetaData
		call.withCatalogName("PKG_ALERT")
			 .withProcedureName("BRK_ACCT_ACC_BY_IM_LIST")
			 .declareParameters(new SqlParameter("P_BROKER_ORG_ID", OracleTypes.NUMBER),
					 new SqlParameter("P_IM_LIST_REF_ID", OracleTypes.VARCHAR),
					 new SqlParameter("P_ACCESS_CODE", OracleTypes.VARCHAR),
					 new SqlParameter("P_COUNTRY", OracleTypes.VARCHAR),
					 new SqlParameter("P_SECURITY", OracleTypes.VARCHAR),
					 new SqlParameter("P_METHOD", OracleTypes.VARCHAR),
					 new SqlParameter("P_XREF", OracleTypes.VARCHAR),
					 new SqlParameter("P_PAGE_SIZE", OracleTypes.NUMBER),
					 new SqlParameter("P_PAGE_NUMBER", OracleTypes.NUMBER),
					 new SqlParameter("P_SORT_COL", OracleTypes.VARCHAR),
					 new SqlParameter("P_SORT_ORDER", OracleTypes.VARCHAR),
					 new SqlOutParameter("P_RESULT", OracleTypes.CURSOR, new BIAXrefSearchResultMapper()))
			 .compile();
		return call;
	}

	@Override
	public DevSimpleJdbcCall countBrokerAccountThroughIMListSearchResults() {
		DevSimpleJdbcCall call = new DevSimpleJdbcCall(dataSourceConfig.alertReadDataSource());
		//FIXME externalize these strings to StoredProcMetaData
		call.withCatalogName("PKG_ALERT")
			.withProcedureName("BRK_ACCT_ACC_BY_IM_LIST_COUNT")
			 .declareParameters(new SqlParameter("P_BROKER_ORG_ID", OracleTypes.NUMBER),
					 new SqlParameter("P_IM_LIST_REF_ID", OracleTypes.VARCHAR),
					 new SqlParameter("P_ACCESS_CODE", OracleTypes.VARCHAR),
					 new SqlParameter("P_COUNTRY", OracleTypes.VARCHAR),
					 new SqlParameter("P_SECURITY", OracleTypes.VARCHAR),
					 new SqlParameter("P_METHOD", OracleTypes.VARCHAR),
					 new SqlParameter("P_XREF", OracleTypes.VARCHAR),
					 new SqlOutParameter("P_ROW_COUNT", OracleTypes.NUMBER))
			 .compile();
		return call;
	}
	
	
	@Override @Bean
	public DevSimpleJdbcCall brokerAsiDataSearch() {
		DevSimpleJdbcCall call = new DevSimpleJdbcCall(dataSourceConfig.alertReadDataSource());
		//FIXME externalize these strings to StoredProcMetaData
		call.withCatalogName("PKG_ALERT")
			 .withProcedureName("BRK_ACCT_SI_DATA_SEARCH")
			 .declareParameters(new SqlParameter("P_BROKER_ORG_ID", OracleTypes.NUMBER),
					 new SqlParameter("P_IM_ORG_ID", OracleTypes.NUMBER),
					 new SqlParameter("P_ACCESS_CODE", OracleTypes.VARCHAR),
					 new SqlParameter("P_COUNTRY", OracleTypes.VARCHAR),
					 new SqlParameter("P_SECURITY", OracleTypes.VARCHAR),
					 new SqlParameter("P_METHOD", OracleTypes.VARCHAR),
					 new SqlParameter("P_XREF", OracleTypes.VARCHAR),
					 new SqlParameter("P_PURPOSE", OracleTypes.VARCHAR),
					 new SqlParameter("P_PAGE_SIZE", OracleTypes.NUMBER),
					 new SqlParameter("P_PAGE_NUMBER", OracleTypes.NUMBER),
					 new SqlParameter("P_SORT_COL", OracleTypes.VARCHAR),
					 new SqlParameter("P_SORT_ORDER", OracleTypes.VARCHAR),
					 new SqlOutParameter("P_RESULT", OracleTypes.CURSOR, new BrokerAcctSISearchResultMapper()))
			 .compile();
		return call;
	}

	@Override
	public DevSimpleJdbcCall countBrokerAsiDataSearchResults() {
		DevSimpleJdbcCall call = new DevSimpleJdbcCall(dataSourceConfig.alertReadDataSource());
		//FIXME externalize these strings to StoredProcMetaData
		call.withCatalogName("PKG_ALERT")
			.withProcedureName("BRK_ACCT_SI_DATA_SEARCH_COUNT")
			 .declareParameters(new SqlParameter("P_BROKER_ORG_ID", OracleTypes.NUMBER),
					 new SqlParameter("P_IM_ORG_ID", OracleTypes.NUMBER),
					 new SqlParameter("P_ACCESS_CODE", OracleTypes.VARCHAR),
					 new SqlParameter("P_COUNTRY", OracleTypes.VARCHAR),
					 new SqlParameter("P_SECURITY", OracleTypes.VARCHAR),
					 new SqlParameter("P_METHOD", OracleTypes.VARCHAR),
					 new SqlParameter("P_XREF", OracleTypes.VARCHAR),
					 new SqlParameter("P_PURPOSE", OracleTypes.VARCHAR),
					 new SqlOutParameter("P_ROW_COUNT", OracleTypes.NUMBER))
			 .compile();
		return call;
	}
	
	@Override
	public DevSimpleJdbcCall gcAccountDataSearch() {
		DevSimpleJdbcCall call = new DevSimpleJdbcCall(dataSourceConfig.alertReadDataSource());
		//FIXME externalize these strings to StoredProcMetaData
		call.withCatalogName("PKG_ALERT")
			.withProcedureName("GC_ACCOUNT_SEARCH")
			 .declareParameters(new SqlParameter("P_GC_ORG_ID", OracleTypes.NUMBER),
					 new SqlParameter("P_COUNTRY", OracleTypes.VARCHAR),
					 new SqlParameter("P_SECURITY", OracleTypes.VARCHAR),
					 new SqlParameter("P_ACCESS_CODE", OracleTypes.VARCHAR),
					 new SqlParameter("P_IM_ORG_ID", OracleTypes.NUMBER),
					 new SqlParameter("P_MODEL_NAME", OracleTypes.VARCHAR),
					 new SqlParameter("P_PAGE_SIZE", OracleTypes.NUMBER),
					 new SqlParameter("P_PAGE_NUMBER", OracleTypes.NUMBER),
					 new SqlParameter("P_SORT_COL", OracleTypes.VARCHAR),
					 new SqlParameter("P_SORT_ORDER", OracleTypes.VARCHAR),
					 new SqlOutParameter("P_RESULT", OracleTypes.CURSOR, new GCAccountSearchResultMapper()))
			 .compile();
		return call;
	}
	
	@Override
	public DevSimpleJdbcCall countGcAccountDataSearch() {
		DevSimpleJdbcCall call = new DevSimpleJdbcCall(dataSourceConfig.alertReadDataSource());
		//FIXME externalize these strings to StoredProcMetaData
		call.withCatalogName("PKG_ALERT")
			.withProcedureName("GC_ACCOUNT_SEARCH_COUNT")
			 .declareParameters(new SqlParameter("P_GC_ORG_ID", OracleTypes.NUMBER),
					 new SqlParameter("P_COUNTRY", OracleTypes.VARCHAR),
					 new SqlParameter("P_SECURITY", OracleTypes.VARCHAR),
					 new SqlParameter("P_ACCESS_CODE", OracleTypes.VARCHAR),
					 new SqlParameter("P_IM_ORG_ID", OracleTypes.NUMBER),
					 new SqlParameter("P_MODEL_NAME", OracleTypes.VARCHAR),
					 new SqlOutParameter("P_ROW_COUNT", OracleTypes.NUMBER))
			 .compile();
		return call;
	}
	
	@Override
	public DevSimpleJdbcCall reportSearch() {
		DevSimpleJdbcCall call = new DevSimpleJdbcCall(dataSourceConfig.alertReadDataSource());
		//FIXME externalize these strings to StoredProcMetaData
		call.withCatalogName("PKG_SEARCH")
			.withProcedureName("ACCOUNT_SEARCH")
			 .declareParameters(new SqlParameter("P_CALLER_ID", OracleTypes.NUMBER),
					 new SqlParameter("P_ACCESS_CODE", OracleTypes.VARCHAR),
					 new SqlParameter("P_GC_ACRO", OracleTypes.VARCHAR),
					 new SqlParameter("P_SECURITY", OracleTypes.VARCHAR),
					 new SqlParameter("P_METHOD", OracleTypes.VARCHAR),
					 new SqlParameter("P_COUNTRY", OracleTypes.VARCHAR),
					 new SqlOutParameter("P_ACCT_DETAIL", OracleTypes.CURSOR, new AccountRefIdMapper()))
			 .compile();
		return call;
	}
	
	@Override @Bean
	public DevSimpleJdbcCall modelSiList() {
		DevSimpleJdbcCall call = new DevSimpleJdbcCall(dataSourceConfig.alertReadDataSource());
		//FIXME externalize these strings to StoredProcMetaData
		call.withCatalogName("PKG_SI_SEARCH")
			 .withProcedureName("GET_MODEL_SI_LIST")
			 .declareParameters(new SqlParameter("P_MODEL_REF_ID", OracleTypes.VARCHAR),
					 new SqlParameter("P_MODEL_NAME", OracleTypes.VARCHAR),
					 new SqlParameter("P_MODEL_OWNER_ORG_ID", OracleTypes.NUMBER),
					 new SqlParameter("P_COUNTRY", OracleTypes.VARCHAR),
					 new SqlParameter("P_SECURITY", OracleTypes.VARCHAR),
					 new SqlParameter("P_METHOD", OracleTypes.VARCHAR),
					 new SqlParameter("P_PURPOSE", OracleTypes.VARCHAR),
					 new SqlParameter("P_PAGE_SIZE", OracleTypes.NUMBER),
					 new SqlParameter("P_PAGE_NUMBER", OracleTypes.NUMBER),
					 new SqlParameter("P_SORT_COL", OracleTypes.VARCHAR),
					 new SqlParameter("P_SORT_ORDER", OracleTypes.VARCHAR),
					 new SqlOutParameter("P_RESULT", OracleTypes.CURSOR, new SIDetailsRowMapper()))
			 .compile();
		return call;
	}

	@Override
	public DevSimpleJdbcCall countModelSiList() {
		DevSimpleJdbcCall call = new DevSimpleJdbcCall(dataSourceConfig.alertReadDataSource());
		//FIXME externalize these strings to StoredProcMetaData
		call.withCatalogName("PKG_SI_SEARCH")
			.withProcedureName("GET_MODEL_SI_LIST_COUNT")
			 .declareParameters(new SqlParameter("P_MODEL_REF_ID", OracleTypes.VARCHAR),
					 new SqlParameter("P_MODEL_NAME", OracleTypes.VARCHAR),
					 new SqlParameter("P_MODEL_OWNER_ORG_ID", OracleTypes.NUMBER),
					 new SqlParameter("P_COUNTRY", OracleTypes.VARCHAR),
					 new SqlParameter("P_SECURITY", OracleTypes.VARCHAR),
					 new SqlParameter("P_METHOD", OracleTypes.VARCHAR),
					 new SqlParameter("P_PURPOSE", OracleTypes.VARCHAR),
					 new SqlOutParameter("P_ROW_COUNT", OracleTypes.NUMBER))
			 .compile();
		return call;
	}
	
	@Override @Bean
	public DevSimpleJdbcCall accountSiList() {
		DevSimpleJdbcCall call = new DevSimpleJdbcCall(dataSourceConfig.alertReadDataSource());
		//FIXME externalize these strings to StoredProcMetaData
		call.withCatalogName("PKG_SI_SEARCH")
			 .withProcedureName("GET_ACCT_SI_LIST")
			 .declareParameters(new SqlParameter("P_ACCT_REF_ID", OracleTypes.VARCHAR),
					 new SqlParameter("P_ACCESS_CODE", OracleTypes.VARCHAR),
					 new SqlParameter("P_ACCT_OWNER_ORG_ID", OracleTypes.NUMBER),
					 new SqlParameter("P_SI_CONTROLLER_ORG_ID", OracleTypes.NUMBER),
					 new SqlParameter("P_COUNTRY", OracleTypes.VARCHAR),
					 new SqlParameter("P_SECURITY", OracleTypes.VARCHAR),
					 new SqlParameter("P_METHOD", OracleTypes.VARCHAR),
					 new SqlParameter("P_PURPOSE", OracleTypes.VARCHAR),
					 new SqlParameter("P_EFFECTIVE_DATE", OracleTypes.DATE),
					 new SqlParameter("P_PAGE_SIZE", OracleTypes.NUMBER),
					 new SqlParameter("P_PAGE_NUMBER", OracleTypes.NUMBER),
					 new SqlParameter("P_SORT_COL", OracleTypes.VARCHAR),
					 new SqlParameter("P_SORT_ORDER", OracleTypes.VARCHAR),
					 new SqlParameter("P_ACTIVE_MSI_ONLY", OracleTypes.NUMBER),
					 new SqlOutParameter("P_RESULT", OracleTypes.CURSOR, new ColumnMapRowMapper()))
			 .compile();
		return call;
	}

	@Override
	public DevSimpleJdbcCall countAccountSiList() {
		DevSimpleJdbcCall call = new DevSimpleJdbcCall(dataSourceConfig.alertReadDataSource());
		//FIXME externalize these strings to StoredProcMetaData
		call.withCatalogName("PKG_SI_SEARCH")
			.withProcedureName("GET_ACCT_SI_LIST_COUNT")
			 .declareParameters(new SqlParameter("P_ACCT_REF_ID", OracleTypes.VARCHAR),
					 new SqlParameter("P_ACCESS_CODE", OracleTypes.VARCHAR),
					 new SqlParameter("P_ACCT_OWNER_ORG_ID", OracleTypes.NUMBER),
					 new SqlParameter("P_SI_CONTROLLER_ORG_ID", OracleTypes.NUMBER),
					 new SqlParameter("P_COUNTRY", OracleTypes.VARCHAR),
					 new SqlParameter("P_SECURITY", OracleTypes.VARCHAR),
					 new SqlParameter("P_METHOD", OracleTypes.VARCHAR),
					 new SqlParameter("P_PURPOSE", OracleTypes.VARCHAR),
					 new SqlParameter("P_EFFECTIVE_DATE", OracleTypes.DATE),
					 new SqlParameter("P_ACTIVE_MSI_ONLY", OracleTypes.NUMBER),
					 new SqlOutParameter("P_ROW_COUNT", OracleTypes.NUMBER))
			 .compile();
		return call;
	}
	
	@Override
	public DevSimpleJdbcCall getSiByBusinessKeys() {
		DevSimpleJdbcCall call = new DevSimpleJdbcCall(dataSourceConfig.alertReadDataSource());
		//FIXME externalize these strings to StoredProcMetaData
		call.withCatalogName("PKG_SI_RETRIEVAL")
			.withProcedureName("GET_SI_BY_BUSINESS_KEYS")
			 .declareParameters(new SqlParameter("P_ACCESS_CODE", OracleTypes.VARCHAR),
					 new SqlParameter("P_OWNER_ORG_ID", OracleTypes.NUMBER),
					 new SqlParameter("P_COUNTRY", OracleTypes.VARCHAR),
					 new SqlParameter("P_SECURITY", OracleTypes.VARCHAR),
					 new SqlParameter("P_METHOD", OracleTypes.VARCHAR),
					 new SqlParameter("P_PURPOSE", OracleTypes.VARCHAR),
					 new SqlParameter("P_SI_TYPE", OracleTypes.VARCHAR),
					 new SqlParameter("P_EFFECTIVE_DATE", OracleTypes.DATE),
					 new SqlParameter("P_BIA", OracleTypes.VARCHAR),
					 new SqlParameter("P_BROKER_ACRONYM", OracleTypes.VARCHAR),
					 new SqlOutParameter("P_RESULT", OracleTypes.CURSOR, new ColumnMapRowMapper()))
			 .compile();
		return call;
		
		}
	
	@Override
	public DevSimpleJdbcCall siListSearch(){
		DevSimpleJdbcCall call = new DevSimpleJdbcCall(dataSourceConfig.alertReadDataSource());
		//FIXME externalize these strings to StoredProcMetaData
		call.withCatalogName("PKG_SEARCH")
			.withProcedureName("ACCOUNT_SI_LIST_SEARCH")
			 .declareParameters(new SqlParameter("P_ACCT_OWNER_ORG_ID", OracleTypes.NUMBER),
					 new SqlParameter("P_ACCESS_CODE", OracleTypes.VARCHAR),
					 new SqlParameter("P_SECURITY", OracleTypes.VARCHAR),
					 new SqlParameter("P_METHOD", OracleTypes.VARCHAR),
					 new SqlParameter("P_COUNTRY", OracleTypes.VARCHAR),
					 new SqlParameter("P_MODEL_ACRO", OracleTypes.VARCHAR),
					 new SqlParameter("P_MODEL_NAME", OracleTypes.VARCHAR),
					 new SqlParameter("P_SI_OWNER_ACRONYM", OracleTypes.VARCHAR),
					 new SqlParameter("P_ACTIVEFLAG",  OracleTypes.NUMBER),
					 new SqlParameter("P_LIKEFLAG",  OracleTypes.NUMBER),
					 new SqlOutParameter("P_SI_LIST", OracleTypes.CURSOR, new ColumnMapRowMapper()))
			 .compile();
		return call;
		
	}
	
	@Override
	public DevSimpleJdbcCall countsiListSearchResults(){
		DevSimpleJdbcCall call = new DevSimpleJdbcCall(dataSourceConfig.alertReadDataSource());
		call.withCatalogName("PKG_SEARCH")
		.withProcedureName("ACCOUNT_SI_LIST_SEARCH_CNT")
		 .declareParameters(new SqlParameter("P_ACCT_OWNER_ORG_ID", OracleTypes.NUMBER),
				 new SqlParameter("P_ACCESS_CODE", OracleTypes.VARCHAR),
				 new SqlParameter("P_SECURITY", OracleTypes.VARCHAR),
				 new SqlParameter("P_METHOD", OracleTypes.VARCHAR),
				 new SqlParameter("P_COUNTRY", OracleTypes.VARCHAR),
				 new SqlParameter("P_MODEL_ACRO", OracleTypes.VARCHAR),
				 new SqlParameter("P_MODEL_NAME", OracleTypes.VARCHAR),
				 new SqlParameter(" P_SI_OWNER_ACRONYM", OracleTypes.VARCHAR),
				 new SqlParameter("P_ACTIVEFLAG",  OracleTypes.NUMBER),
				 new SqlParameter("P_LIKEFLAG",  OracleTypes.NUMBER),
				 new SqlOutParameter("P_ROW_COUNT", OracleTypes.NUMBER))
		 .compile();
		return call;
	}
	@Override
	public DevSimpleJdbcCall brokerMultiAccountSearch(){
		DevSimpleJdbcCall call = new DevSimpleJdbcCall(dataSourceConfig.alertReadDataSource());
		call.withCatalogName("PKG_ALERT")
		.withProcedureName("SEARCH_MULTI_ACC_BY_ACRO")
		 .declareParameters(new SqlParameter("P_BROKER_ORG_ID", OracleTypes.NUMBER),
				 new SqlParameter("P_SEARCH_LIST", OracleTypes.ARRAY, "ACRO_ACCESS_LIST_TAB"),
				 new SqlOutParameter("P_RESULT", OracleTypes.CURSOR, new AccountBIADetailsMapper()))
				
		 .compile();
		return call;
		
	}
	
	@Override
	public DevSimpleJdbcCall brokerMultiBiaSearch(){
		DevSimpleJdbcCall call = new DevSimpleJdbcCall(dataSourceConfig.alertReadDataSource());
		call.withCatalogName("PKG_ALERT")
		.withProcedureName("SEARCH_MULTI_ACC_BY_BIA")
		 .declareParameters(new SqlParameter("P_BROKER_ORG_ID", OracleTypes.NUMBER),
				 new SqlParameter("P_SEARCH_LIST", OracleTypes.ARRAY, "BIA_LIST_TAB"),
				 new SqlOutParameter("P_RESULT", OracleTypes.CURSOR, new AccountBIADetailsMapper()))
				
		 .compile();
		return call;
		
	}
	
/* R3 | B-11825
 * */	
	@Override 
	public DevSimpleJdbcCall imAsiDataSearch() {
		DevSimpleJdbcCall call = new DevSimpleJdbcCall(dataSourceConfig.alertReadDataSource());
		//FIXME externalize these strings to StoredProcMetaData
		call.withCatalogName("PKG_REPORTING")
			 .withProcedureName("ACCT_SI_RPT_SP")
			 .declareParameters(new SqlParameter("P_ACCT_REF_ID", OracleTypes.VARCHAR),
					 new SqlParameter("P_SECURITY", OracleTypes.VARCHAR),
					 new SqlParameter("P_METHOD", OracleTypes.VARCHAR),
					 new SqlParameter("P_COUNTRY", OracleTypes.VARCHAR),
					 new SqlParameter("P_PURPOSE", OracleTypes.VARCHAR),
					 new SqlOutParameter("P_RESULT", OracleTypes.CURSOR, new ColumnMapRowMapper()))
			 .compile();
		return call;
	}

	/* R3 | B-22866
	 * */	
		@Override 
		public DevSimpleJdbcCall msg48AsiDataSearch() {
			DevSimpleJdbcCall call = new DevSimpleJdbcCall(dataSourceConfig.alertReadDataSource());
			
			call.withCatalogName("PKG_SI_RETRIEVAL")
				 .withProcedureName("GET_MSG48_SI_DETAILS")
				 .declareParameters(new SqlParameter("P_ACCT_OWNER_ORG_ID", OracleTypes.NUMBER),
						 new SqlParameter("P_BROKER_ORG_ID", OracleTypes.NUMBER),
						 new SqlParameter("P_ACCESS_CODE", OracleTypes.VARCHAR),
						 new SqlParameter("P_COUNTRY", OracleTypes.VARCHAR),
						 new SqlParameter("P_SECURITY", OracleTypes.VARCHAR),
						 new SqlParameter("P_METHOD", OracleTypes.VARCHAR),
						 new SqlParameter("P_PURPOSE", OracleTypes.VARCHAR),
						 new SqlOutParameter("P_RESULT", OracleTypes.CURSOR, new ColumnMapRowMapper()))
				 .compile();
			return call;
		}	
		
		@Override 
		public DevSimpleJdbcCall partialAccessCodeAsiDataSearch() {
			DevSimpleJdbcCall call = new DevSimpleJdbcCall(dataSourceConfig.alertReadDataSource());
			
			call.withCatalogName("PKG_SI_RETRIEVAL")
				 .withProcedureName("GET_SI_BY_LIKE_ACCODE")
				 .declareParameters(new SqlParameter("P_ACCT_OWNER_ORG_ID", OracleTypes.NUMBER),
						 new SqlParameter("P_BROKER_ORG_ID", OracleTypes.NUMBER),
						 new SqlParameter("P_ACCESS_CODE", OracleTypes.VARCHAR),
						 new SqlParameter("P_COUNTRY", OracleTypes.VARCHAR),
						 new SqlParameter("P_SECURITY", OracleTypes.VARCHAR),
						 new SqlParameter("P_METHOD", OracleTypes.VARCHAR),
						 new SqlParameter("P_PURPOSE", OracleTypes.VARCHAR),
						 new SqlOutParameter("P_RESULT", OracleTypes.CURSOR, new ColumnMapRowMapper()))
				 .compile();
			return call;
		}	
}



---
DevSimpleJdbcCall  accountSiSearchCount
	@Override
	public int countSearchResults(String accountRefId, String accessCode,
						Organization accountOwner, Organization siController, Country c,
						Security s, Method m, String purpose,  Boolean activeOnly, Date effectiveDate) {
		final MapBuilder<String,Object> params = new MapBuilder<>();
		params.put("P_ACCT_REF_ID", accountRefId)
		 	  .put("P_ACCT_OWNER_ORG_ID", accountOwner == null ? null : accountOwner.getOrgId())
		 	  .put("P_ACCESS_CODE", accessCode)
		 	  .put("P_SI_CONTROLLER_ORG_ID", siController == null ? null : siController.getOrgId())
		 	  .put("P_COUNTRY", c==null ? null : c.getIsoCode())
		 	  .put("P_SECURITY", s==null ? null : s.getIsoCode())
		 	  .put("P_METHOD",  m==null ? null : m.getMethodCode())
		 	  .put("P_PURPOSE", purpose)
		 	  .put("P_EFFECTIVE_DATE", effectiveDate)
		 	  .put("P_ACTIVE_MSI_ONLY", activeOnly == null ? 0 : activeOnly ? 1 : 0);
		Map<String,Object> results = accountSiSearchCount.execute(params.toMap());
		BigDecimal total = results.containsKey("P_ROW_COUNT") ? 
				(BigDecimal) results.get("P_ROW_COUNT") : 
				new BigDecimal(0);
		return total.intValue();
	}
