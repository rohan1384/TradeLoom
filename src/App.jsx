import { useEffect, useState } from 'react'
import './App.css'
import Navbar from './page/Navbar/Navbar'
import Home from './page/Home/Home'
import { Route, Routes } from 'react-router-dom'
import Portfolio from './page/Portfolio/Portfolio'
import Activity from './page/Activity/Activity'
import Wallet from './page/Wallet/Wallet'
import Withdrawal from './page/Withdrawal/Withdrawal'
import PaymentDetails from './page/Payment Details/PaymentDetails'
import StockDetails from './page/Stock Details/StockDetails'
import Watchlist from './page/Watchlist/Watchlist'
import Profile from './page/Profile/Profile'
import SearchCoin from './page/Search/SearchCoin'
import Notfound from './page/Notfound/Notfound'
import Auth from './page/Auth/Auth'
import { useDispatch, useSelector } from 'react-redux'
import { store } from './State/Store'
import { getUser } from './State/Auth/Action'

function App() {
  const [count, setCount] = useState(0)
           
  const {auth}=useSelector(store=>store)
  const dispatch=useDispatch()
  console.log("auth:",auth);

  useEffect(()=>{
    dispatch( getUser(auth.jwt || localStorage.getItem("jwt")))
  },[auth.jwt])
  return (
    <>
   
    {auth.user ?
     <div>
     <Navbar/>
     <Routes>
      <Route path="/" element={<Home/>}></Route>
      <Route path="/portfolio" element={<Portfolio/>}></Route>
      <Route path="/activity" element={<Activity/>}></Route>
      <Route path="/wallet" element={<Wallet/>}></Route>
      <Route path="/withdrawal" element={<Withdrawal/>}></Route>
      <Route path="/payment-details" element={<PaymentDetails/>}></Route>
      <Route path="/market/:id" element={<StockDetails/>}></Route>
      <Route path="/watchlist" element={<Watchlist/>}></Route>
      <Route path="/profile" element={<Profile/>}></Route>
      <Route path="/search" element={<SearchCoin/>}></Route>
      <Route path="*" element={<Notfound/>}></Route>

     
     </Routes>
    
     </div>
     : <Auth/>
   }
    </>
  )
}

export default App
