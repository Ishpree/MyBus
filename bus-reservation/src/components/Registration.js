import {Link} from "react-router-dom"
import Login
 from './Login';
import {react, component} from 'react';
import { useState } from "react";
import axios from 'axios';
import {useNavigate} from 'react-router-dom'
export default function Registration() {

  //The field names name, email_id , password should be same as the field names in the registereduseerdto , 
  //the json file generated from the details entered after registeration will be mapped to the
  //field names present in the object of the registereduserdto
  const [userData, setUserData] = useState({email:"", name: "", password:""});
  const navigate = useNavigate();

  //The ... is the spread operator which is used to expand elements of an iterable or properties 
  //of an object into a new array or object
  const handleInputChange=(event)=>
  {
    const{name, value}=event.target;
    setUserData({...userData, [name]: value});
  }

  const handleRegistration = (event) => {
    event.preventDefault();//to prevent the default submission
  
    axios.post('http://localhost:8083/api/Registration', userData, { withCredentials: true })
      .then((response) => {
        console.log('Registration successful:', response.data); 
        navigate('/Login');
      })
      .catch((error) => {
        console.error('Registration failed:', error);
      });
  };



  return (
    <>
    <form onSubmit={handleRegistration}>
      <div className="form-group">
        <h2 style={{ marginBottom: "10px" }}>Registration</h2>
        <label htmlFor="name">
          Name
        </label>
        <input
          type="text"
          className="form-control"
          id="name"
          placeholder="Enter your name"required
          name="name"
          onChange={handleInputChange}
        />
      </div>
      <div className="form-group">
      <label htmlFor="exampleFormControlInput1">Email address</label>
    <input type="email" className="form-control" id="exampleFormControlInput1" name="email" placeholder="name@example.com"  onChange={handleInputChange}/>
      </div>
      <div className="form-control">
        <label htmlFor="inputPassword5" className="form-label"> 
          Password
        </label>
        <input
          type="password"
          id="inputPassword5"
          className="form-control"
          name="passwor"
          aria-describedby="passwordHelpBlock"
          onChange={handleInputChange}
        />
      </div>
      <button type="submit" className="btn btn-primary">
          Register
        </button>
        Already Registered?
       <Link to='/Login'>Login</Link>
      </form>
    </>
  );
}
