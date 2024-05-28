import logo from './logo.svg';
import './App.css';
import Navbar
from './components/Navbar';
import Login
 from './components/Login';
 import React from 'react';
 import Registration from './components/Registration';
 import {
  BrowserRouter,
  Routes,
  Route,
  Link
} from "react-router-dom";
import Dashboard from './components/Dashboard';
import BookingPage from './components/BookingPage';
import BusData
from './components/BusData';
import Booked from './components/Booked';
import { useState } from 'react';
import AllBookings from './components/AllBookings';

function App() {

  const [backendData, setBackendData] = useState(null);
  const[bookingData, setBookingData]=useState(null);
  const [booked, setBooked] = useState(null);
  const[myseat, setSeat]=useState(null);
  const[seatNos, setSeatNos]=useState([]);
  const[listBookings, setListOfBookings]=useState([]);

  const doSetBackend=(backendData)=>
  {
    setBackendData(backendData);
  }

  const doSetBooking=(bookingData)=>
  {
    setBookingData(bookingData);
  }

  const doSetBooked=(booked)=>
  {
    console.log(booked,"book test");
    setBooked(booked);
  }

  const doSetSeat=(myseat)=>
  {
    setSeat(myseat);
  }

  const doSetSelectedSeat=(updatedSeatNos)=>
  {
      setSeatNos(updatedSeatNos);
  }

  const doSetAllBookings=(updatedBookingList)=>
  {
    setListOfBookings(updatedBookingList);
  }


  return (
    <>
     <BrowserRouter>
      <Navbar title="Bus Reservation System" listBookings={listBookings} allBookings={doSetAllBookings} />
      <div className="container my-3">
    <Routes>
      <Route path="/Registration" element={<Registration/>}/>
      <Route path="/Login" element={<Login/>}/>
      <Route path="/Dashboard" element={<Dashboard backendData={backendData} backend={doSetBackend}/>}></Route>
      <Route path="/Dashboard/BusData" element={<BusData backendData={backendData} backend={doSetBackend} bookingData={bookingData} booking={doSetBooking} myseat={myseat} userSeat={doSetSeat}/>}></Route>
      <Route path="/Dashboard/Booking" element={<BookingPage bookingData={bookingData} booking={doSetBooking} booked={booked} payed={doSetBooked} myseat={myseat} userSeat={doSetSeat} seatNos={seatNos} allSeats={doSetSelectedSeat}/>}></Route>
      <Route path="/Dashboard/Booked"  element={<Booked booked={booked} payed={doSetBooked} seatNos={seatNos} allSeats={doSetSelectedSeat} />}></Route>
      <Route path="/AllBookings" element={<AllBookings listBookings={listBookings} allBookings={doSetAllBookings} />}></Route>
    </Routes>
         </div> 
    </BrowserRouter>
     </>
  );
}

export default App;
