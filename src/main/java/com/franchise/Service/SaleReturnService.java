package com.franchise.Service;

 
import java.util.List;
import java.util.Optional;

 import com.franchise.Entity.SaleReturn;

public interface SaleReturnService {

	
	SaleReturn saveSaleReturn(SaleReturn saleReturn);
		
		List<SaleReturn> getAllSaleReturn();
		
		
	    Optional<SaleReturn> getSaleReturnById(Long id);

	    SaleReturn updateSaleReturn(Long id, SaleReturn saleReturn); // New method for update
	    
	    void deleteSaleReturn(Long id); // New method for delete
	    
	    public List<String> getAllReturnIds();
	    
	    Optional<SaleReturn> getSaleOrderById(String orderId);
	    


}