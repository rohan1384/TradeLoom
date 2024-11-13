import api from "@/Config/api";
import * as types from "./ActionType"


export const getUserWallet=(jwt)=>async(dispatch)=>{

    dispatch({type:types.GET_USER_WALLET_REQUEST})


   try{
       const response=await api.get("/api/wallet",{
        headers:{
            Authorization :`Bearer ${jwt}`,
        }
       });
        
       
        dispatch({type:types.GET_USER_WALLET_SUCCESS,payload:response.data})
                  console.log("user wallet:",response.data)

   }
   catch(error){
       dispatch({type:types.GET_USER_WALLET_FAILURE,payload:error.message})

       console.log(error);


   }
}


export const getWalletTransactions=({jwt})=>async(dispatch)=>{

    dispatch({type:types.GET_USER_WALLET_TRANSACTION_REQUEST})

   

   try{
       const response=await api.get("/api/wallet/transactions",{
        headers:{
            Authorization:`Bearer ${jwt}`,
        }
       });
        
        console.log("wallet transaction",response.data);
        dispatch({type:types.GET_USER_WALLET_TRANSACTION_SUCCESS ,payload:response.data})


   }
   catch(error){
       dispatch({type:types.GET_USER_WALLET_TRANSACTION_FAILURE,payload:error.message})

       console.log(error);


   }
}



export const depositMoney=({jwt,orderId,paymentId,navigate})=>async(dispatch)=>{

    dispatch({type:types.DEPOSITE_MONEY_REQUEST})

   

   try{
       const response=await api.put(`/api/wallet/deposit`,null,{
        params:{
            order_id:orderId,
            payment_id:paymentId,
        },

        headers:{
            Authorization:`Bearer ${jwt}`
        }
       });
        
        dispatch({type:types.DEPOSITE_MONEY_SUCCESS,payload:response.data})
            
        navigate("/wallet")
        console.log(response.data)

   }

   catch(error){
       dispatch({type:types.DEPOSITE_MONEY_FAILURE,payload:error.message})

       console.log(error);


   }
}




export const paymentHandler=({jwt,amount,paymentMethod})=>async(dispatch)=>{

    dispatch({type:types.DEPOSITE_MONEY_REQUEST})

   

   try{
       const response=await api.post(`/api/payment/${paymentMethod}/amount/${amount}`,
        null,{
            headers:{
                Authorization:`Bearer ${jwt}`
            }
        }
       );
        
           window.location.href=response.data.payment_url;
        
      // dispatch({type:types.DEPOSITE_MONEY_SUCCESS,payload:response.data})


   }
   catch(error){
       dispatch({type:types.DEPOSITE_MONEY_FAILURE,error:error.message})

       console.log(error);


   }
}




export const transferMoney=({jwt,walletId,reqData})=>async(dispatch)=>{

    dispatch({type:types.TRANSFER_MONEY_REQUEST})

   

   try{
       const response=await api.put(`/api/wallet/${walletId}/transfer`,
        reqData,
        {
        headers:{
            Authorization:`Bearer ${jwt}`
        }
       });
        
        dispatch({type:types.TRANSFER_MONEY_SUCCESS,payload:response.data})


   }
   catch(error){
       dispatch({type:types.TRANSFER_MONEY_FAILURE,error:error.message})

       console.log(error);


   }
}


