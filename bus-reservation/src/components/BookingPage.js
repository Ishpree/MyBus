
import React, { useState } from "react";
import { useEffect } from "react";
import { useLocation
} from "react-router-dom";
import axios from "axios";
import { useNavigate } from "react-router-dom";

//useLocation provides access to the current URL, search parameters, and, importantly, the state object passed with navigate.
//Optional chaining (?.) is recommended to handle cases where the state object might be missing.

export default function BookingPage(props){

  const [people, setNoOfPeople] = useState(0);
  const [totalPrice, setPrice] = useState(0);
  const [isSelected, setIsSelected] = useState(new Array(20).fill(false));
  const navigate = useNavigate();
  let s=[];
  s=props.myseat;

  // console.log(s);

  const handleClick = (seatIndex) => {
    const newSelectedSeats = [...isSelected];
    if(isSelected[seatIndex])
  {
    newSelectedSeats[seatIndex] = !isSelected[seatIndex];
    setIsSelected(newSelectedSeats);
    setNoOfPeople(people-1);
  }
  else{
    newSelectedSeats[seatIndex] = !isSelected[seatIndex]; // Toggle the clicked seat
    setIsSelected(newSelectedSeats);
    setNoOfPeople(people+1);
  }
  
};

  //console.log(people) results in a zero because state will not immedtiately so we can immediately update total price also
  //so we use useEffect
  // const handleUserInput = (event) => {
  //   const n = parseInt(event.target.value);
  //   setNoOfPeople(n);
  //   console.log(people);
  // };

  //The state of total price does not get automatially updated if we update it just after serNOofPeople(n)
  //useEffect automatically gets triggered when one of the things specified in the dependency array changes
  //If I try to print the console.log(totalPrice) without adding totalPrice dependency it wont print but
  //when I add it in the dependency array when after some time the the state we updated through setPrice(totalPrice)
  //actually gets updated then the useEffect function gets called again and totalPrice gets printed.

  useEffect(() => {
    if (props.bookingData) {
      console.log(props.bookingData);
      const newTotal = people * props.bookingData.price;
      setPrice(newTotal);
      console.log(totalPrice);
    }
  }, [people, props.bookingData, totalPrice]);

  const handlePay = async (event) => {
    event.preventDefault();
    console.log(totalPrice);
    const updatedSeatNos = [];
    for(let i=0;i<20;i++)
    {
      if(isSelected[i]){
      s[i]['availability']= false;
      const{id}=props.bookingData;
      updatedSeatNos.push({my_seat:s[i].seatKey.seat_no}); // Add object to updatedSeatNos
    }
  }
  console.log(updatedSeatNos);
  props.allSeats(updatedSeatNos);
  console.log(props.seatNos);
  
    const updatedBookingDto = {
      available_seats: (props.bookingData.available_seats)-people,
      filter_date: props.bookingData.filter_date,
      to_destination: props.bookingData.to_destination,
      from_location: props.bookingData.from_location,
      price: props.bookingData.price,
      bus_name: props.bookingData.bus_name,
      time: props.bookingData.time,
      noOfPeople: people,
      total: totalPrice,
      seats: s,
      seat_nos: updatedSeatNos,
      version:props.bookingData.version,
      id:props.bookingData.id
    };
    console.log(updatedBookingDto);

    await axios
      .post("http://localhost:8083/api/Dashboard/Booking", updatedBookingDto, {
        withCredentials: true,
      })
      .then((response) => {
        console.log(response.data);
        const a=(response.data).BookingsDTO;
        const temp={booking_id:(response.data).booking_id};
        response.data.BookingsDTO=[a, temp];
        props.payed(a);
        navigate("/Dashboard/Booked");
      });
  };

   
    // const location = useLocation();
  
    //If the dependency array is empty ([]), the useEffect hook only runs once, after the initial render of the component. 
    // useEffect(() => {
    //   const receivedBookingData = props.bookingData;
    //   setBookingData(receivedBookingData);
    //   console.log(props.bookingData); // Set initial bookingData state
    // }, []);
     

    //that state updates in React are asynchronous. The setTotal function might not immediately reflect 
    //the updated value in the UI. To display the updated total immediately after state change, you can
    // use the useEffect hook with an empty dependency array:

   // When the dependency array includes one or more values (e.g., state variables, props), the useEffect hook runs whenever any of the values in the dependency array change.

    return(
      <>
        {props.bookingData &&

        <div className="container">
        <h1>Booking Details</h1>
        <form>
          <div className="form-group row">
            <label htmlFor="name" className="col-sm-2 col-form-label">Bus Name</label>
            <div className="col-sm-10">
              <input  type="text"
                className="form-control"
                id="name"
                value={props.bookingData.bus_name} // Set initial value from bookingData
                readOnly // Make the field read-only (optional)
              />
            </div>
          </div>
          <div className="form-group row">
            <label htmlFor="name" className="col-sm-2 col-form-label">Date</label>
            <div className="col-sm-10">
              <input
                type="text"
                className="form-control"
                id="name"
                value={props.bookingData.filter_date} // Set initial value from bookingData
                readOnly // Make the field read-only (optional)
              />
            </div>
          </div>
          <div className="form-group row">
            <label htmlFor="name" className="col-sm-2 col-form-label">From</label>
            <div className="col-sm-10">
              <input
                type="text"
                className="form-control"
                id="name"
                value={props.bookingData.from_location} // Set initial value from bookingData
                readOnly // Make the field read-only (optional)
              />
            </div>
          </div>
          <div className="form-group row">
            <label htmlFor="name" className="col-sm-2 col-form-label">To</label>
            <div className="col-sm-10">
              <input
                type="text"
                className="form-control"
                id="name"
                value={props.bookingData.to_destination} // Set initial value from bookingData
                readOnly // Make the field read-only (optional)
              />
            </div>
          </div>
          <div className="form-group row">
            <label htmlFor="name" className="col-sm-2 col-form-label">Price</label>
            <div className="col-sm-10">
              <input
                type="text"
                className="form-control"
                id="name"
                value={props.bookingData.price} // Set initial value from bookingData
                readOnly // Make the field read-only (optional)
              />
            </div>
          </div>
          
          <div className="form-group row">
            <label htmlFor="name" className="col-sm-2 col-form-label">Time</label>
            <div className="col-sm-10">
              <input
                type="text"
                className="form-control"
                id="name"
                value={props.bookingData.time}
                readOnly
              />
            </div>
          </div>
      
  
          <div className="form-group-row">
            <label htmlFor="name" className="col-sm-2 col-form-label">
              No of People
            </label>
            <div className ="col-sm-10">
              <span id="count">{people}</span>
            </div>
          </div>
          <div className="form-group row">
            <label htmlFor="name" className="col-sm-2 col-form-label">
              Total
            </label>
            <div className="col-sm-10">
              <span id="total">{totalPrice}</span>
            </div>
          </div>
          </form>
          </div>}
      
          {props.bookingData && props.myseat && s.map((dataItem, index) => (
            <div>
            <button
              key={dataItem.seatKey.seat_no}
              type="button"
              data-bs-toggle="button"
              // className={`bus-seat ${isSelected[index] ? "selected" : ""}` `bus-seat-wrapper ${index % 4 === 3 ? 'break-line' : ''}` }
              className="btn"
              disabled={!(dataItem.availability)}
              onClick={() => handleClick(index)}
            >{dataItem.seatKey.seat_no}</button>
             </div>
            // {(index % 2 === 1) ? <span className="seat-spacer">{/* Space after every other button */}</span>: null}  
          ))
        } 
      {props.bookingData && <button
        type="submit"
        disabled={!totalPrice}
        onClick={async (event) => await handlePay(event)}
      >
      Pay
      </button>}
    </>
  )
}