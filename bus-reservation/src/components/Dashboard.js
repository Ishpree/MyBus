import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";
export default function Dashboard(props) {
  const [userSearch, setUserSearch] = useState({
    from_location: "",
    to_destination: "",
    filter_date: "",
  }); 

  let locations = [];
  
  locations = [
    { id: 1, label: "Bengaluru" },
    { id: 2, label: "Hyderabad" },
    { id: 3, label: "Chandigarh" },
    { id: 4, label: "New Delhi" },
  ];

  
  let arr = null;
  const navigate = useNavigate();


  const isFiltered = (date) => {
    const selectedDate = new Date(date); 
    return selectedDate.getDay() === 6 || selectedDate.getDay() === 0 || selectedDate < new Date(); // Use Date object methods
  };

  const handleChange = (event) => {
    const { name, value } = event.target;
    setUserSearch({ ...userSearch, [name]: value });
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    console.log(userSearch);
    await axios
      .post("http://localhost:8083/api/Dashboard", userSearch, {
        withCredentials: true,
      })
      .then((response) => {
        console.log("Bus Data:", response.data);
        const item = response.data;
        //In java the hashmap is unordered hence to find which key contains the busData we used this
        arr = item["busData"];
        // setBackendData(arr);
        console.log(arr);
        props.backend(arr);
        navigate("/Dashboard/BusData");
      })
      .catch((error) => {
        console.error("Could not fetch buses");
      });
  };

  return (
    <>
    <div className="container" style={{height:'200px', marginTop: '70px'}}>
    <form onSubmit={handleSubmit} className="centered-form">
      <div className="input-group" style={{height:'70px', width:'1000px', borderRadius:'0'}}>
        <select
          className="form-control"
          id="exampleFormControlSelect1"
          name="from_location"
          onChange={handleChange}
          value={userSearch.from_location} // Set initial value from state
        >
          <option value="">Select Your Location</option> {/* Default option */}
          {locations.map((location) => (
            <option key={location.id}>
              {location.label}
            </option>
          ))}
        </select>
      
        <select
          className="form-control"
          id="exampleFormControlSelect1"
          name="to_destination"
          onChange={handleChange}
          value={userSearch.to_destination} // Set initial value from state
        >
          <option value="">Select The Destination</option> {/* Default option */}
          {locations.map((location) => (
            <option key={location.id}>
              {location.label}
            </option>
          ))}
        </select>
        
        <div className="form-group">
      {/* <label htmlFor="filterDateInput"></label> */}
      <input
        type="date"
        className="form-control date-picker-square" // Add custom class
        id="filterDateInput"
        name="filter_date"
        placeholder="Select Date"
        value={userSearch.filter_date}
        onChange={handleChange}
        disabled={isFiltered(userSearch.filter_date)} 
        // Disable if filtered (optional)
        style={{height:'70px', width:'250px',borderRadius:'0'}}
      />
    </div>
        <div className="submit-button">
          <button type="submit" className="btn btn-primary" style={{height:'70px', width:'200px',borderTopRightRadius:'10',borderBottomRightRadius:'20',borderBottomLeftRadius:'0',borderTopLeftRadius:'0',backgroundColor:'#333333'}}>
           <b>FIND BUSES</b>
          </button>
        </div>
        </div>
      </form>
      </div>
    </>
  );
}
