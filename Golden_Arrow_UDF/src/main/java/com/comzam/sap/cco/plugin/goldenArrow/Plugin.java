package com.comzam.sap.cco.plugin.goldenArrow;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.scco.ap.plugin.BasePlugin;
import com.sap.scco.ap.plugin.annotation.ListenToExit;
import com.sap.scco.ap.plugin.annotation.ui.JSInject;
import com.sap.scco.ap.pos.dto.ReceiptDTO;
import com.sap.scco.ap.pos.dto.CustomerDTO;

import com.sap.scco.ap.pos.dto.SalesItemDTO;
import com.sap.scco.env.UIEventDispatcher;

import generated.GenericValues;
import generated.GenericValues.KeyValPair;
import generated.MaintainBPType;
import generated.PostInvoiceType;
import net.sf.json.JSONObject;
import generated.PostInvoiceType.Sale.DocumentLines.Row;



public class Plugin extends BasePlugin {

	private static final Logger logger = LoggerFactory.getLogger(Plugin.class);

	public final static String PLUGIN_ID = "Golden_Arrow_UDF";
	
	
	@Override
	public String getId() {
		return PLUGIN_ID;
	}

	@Override
	public String getName() {
		return "Golden_Arrow_UDF";
	}
	@Override
	public boolean persistPropertiesToDB() {
		return true;
	}

	@Override
	public String getVersion() {
		return getClass().getPackage().getImplementationVersion();
	}
	
	@Override
	public void startup() {
		super.startup();
		System.out.println("Hello world, this is Jamal's coding");
	}
	
	//getExternalID
//	  @ListenToExit(exitName = "BusinessOneServiceWrapper.beforeMaintainBP")
//	  public void enlargeB1iMessage(Object caller, Object[] args) {
//	     
//
//	      PostInvoiceType request = (PostInvoiceType)args[1];
//	      ReceiptDTO request1 = (ReceiptDTO)args[0];
//	      List<SalesItemDTO> salesItems = request1.getSalesItems();
//
//	      for(int i = 0; i < salesItems.size(); ++i) {
//	         SalesItemDTO item = (SalesItemDTO)salesItems.get(i);
//	         KeyValPair keyValPair1 = new KeyValPair();
//	         KeyValPair keyValPair2 = new KeyValPair();
//	         KeyValPair keyValPair3 = new KeyValPair();
//	         KeyValPair keyValPair4 = new KeyValPair();
//	         keyValPair1.setKey("CostingCode2");
//	         keyValPair2.setKey("CostingCode3");
//	         keyValPair3.setKey("CostingCode4");
//	         keyValPair4.setKey("CostingCode5");
//
//	         try {
//	            keyValPair1.setValue(item.getMaterial().getUdfString1() == null ? "" : item.getMaterial().getUdfString1().toString());
//	            keyValPair2.setValue(item.getMaterial().getUdfString2() == null ? "" : item.getMaterial().getUdfString2().toString());
//	            keyValPair3.setValue(item.getMaterial().getUdfStringXL1() == null ? "" : item.getMaterial().getUdfStringXL1().toString());
//	            keyValPair4.setValue(item.getMaterial().getUdfStringXL2() == null ? "" : item.getMaterial().getUdfStringXL2().toString());
//	            logger.info("iteration # " + i);
//	            Row row = (Row)request.getSale().getDocumentLines().getRow().get(i);
//	            row.setGenericValues(new GenericValues());
//	            row.getGenericValues().getKeyValPair().add(keyValPair1);
//	            row.getGenericValues().getKeyValPair().add(keyValPair2);
//	            row.getGenericValues().getKeyValPair().add(keyValPair3);
//	            row.getGenericValues().getKeyValPair().add(keyValPair4);
//	         } catch (Exception var13) {
//	            logger.info("errooor" + var13.toString());
//	         }
//	      }
//
//	      args[1] = request;
//	   }
	
	
	@ListenToExit(exitName = "BusinessOneServiceWrapper.beforeMaintainBP")
	public void storeSubChannel(Object caller, Object[] args) {
		
		logger.info("Exit started @@@@ ");

		 MaintainBPType request = (MaintainBPType)args[0];
		 CustomerDTO request2 = (CustomerDTO)args[1];
		 
		 
		 GenericValues.KeyValPair keyValPair = new GenericValues.KeyValPair();
		 
         keyValPair.setKey("Sub_Channel");
         keyValPair.setValue(request2.getUdfString1() == null? "" : request2.getUdfString1().toString());
         
         request.setGenericValues(new GenericValues());
         
         request.getGenericValues().getKeyValPair().add(keyValPair);
         
         logger.info(request2.getUdfString1().toString());
         
         logger.info("End of Exit @@@@@");

		
	}



		    
	
	   public static void showMessageToUi(String msg, String type) {
		    Map<String, String> dialogOptions = new HashMap<>();
		    dialogOptions.put("message", msg);
		    dialogOptions.put("id", Plugin.class.getSimpleName());
		    dialogOptions.put("type", type);
		    dialogOptions.put("maxLifeTime", "30");
		    UIEventDispatcher.INSTANCE.dispatchAction("SHOW_MESSAGE_DIALOG", null, dialogOptions);
		  }
	  public static void showfieldToUi(String msg, String type) {
		    JSONObject dialogOptions = new JSONObject();
		    dialogOptions.put("message", msg);
		    dialogOptions.put("id", "DIALOG_CONFIG");
		    dialogOptions.put("type", "warning");
		    dialogOptions.put("input", "true");
		    JSONObject btnOkConf = new JSONObject();
		    btnOkConf.put("type", "good");
		    btnOkConf.put("id", "DIALOG_CONFIG_BTN_OK");
		    btnOkConf.put("text", "OK");
		    btnOkConf.put("default", "true");
		    JSONObject btnCancelConf = new JSONObject();
		    btnCancelConf.put("type", "bad");
		    btnCancelConf.put("id", "DIALOG_CONFIG_BTN_CAN");
		    btnCancelConf.put("text", "Abort");
		    dialogOptions.put("buttons", new JSONObject[] { btnCancelConf });
		    UIEventDispatcher.INSTANCE.dispatchAction("SHOW_MESSAGE_DIALOG", null, dialogOptions);
		  }
	   
}