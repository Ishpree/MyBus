import {Link} from "react-router-dom"
import Registration from "./Registration"
import { useNavigate } from "react-router-dom";
import {useState} from "react";
import axios from "axios";
import Dashboard from "./Dashboard";


 export default function Login()
 {

  //useState({username: "", password:""}) these field names should match the ones that are there in the 
  //userlogindto for successfull mapping

const [userResponse, setUserResponse] = useState({username: "", password:""});
const navigate =useNavigate();

  const handleInputChanges=(event)=>
  {
    const{name, value}=event.target;
    setUserResponse({...userResponse, [name]: value});
  }

  //.then is used so that the statment afterwards are only executed after the response 
  //sometimes the response is delayed and the console statement will get executed before
  //the reponse comes from the server side , which we dont want

  //name="" field has to be added according to the fieldname that is there in the userlogindto so 
  //that the corrct mapping occurs for example name="username"
  //the withCredentials: true option is set to enable sending cookies across requests, which will 
  //be crucial for maintaining user sessions later.

  //This token I took from postman, when I run the request from postman and select the authorization before 
  //making postman request the token gets generated 
  const token = 'eyJhbGciOiJIUzI1NiJ9.e30.NK8-7s4VFsA--FWjDpq-mXj32r3-yJ6MbXOJb96D9mQ'

  const handleLogin = (event)=>
  {
    event.preventDefault();
    console.log(userResponse);
     axios.post('http://localhost:8083/api/Login', userResponse, {
      headers:{
          'Authorization': `Bearer ${token}`
      }, withCredentials:true}).then((response)=>{
      console.log('Login successful:', response.data);
      navigate('/Dashboard');})
    .catch((error)=>
    {
      console.error('Login failed');
    });
  };

    return(
        <>
     <form onSubmit={handleLogin}>
    <div className="form-group">
    <h2 style={{marginBottom:'10px'}}>Login</h2>
    <label htmlFor="exampleFormControlInput1">Email address</label>
    <input type="email" className="form-control" id="exampleFormControlInput1" name="username" placeholder="name@example.com" onChange={handleInputChanges}/>
  </div>
  <div className="form-group">
        <label htmlFor="inputPassword1" className="form-label"> 
          Password
        </label>
        <input
          type="password"
          id="inputPassword1"
          className="form-control"
          name="password"
          onChange={handleInputChanges}
        />
      </div>

  <button type="submit" className="btn btn-primary me-2">Login</button>
  Not Registered?
  <Link to="/Registration">Register Now</Link>

  </form>
   </>
  
  )
 }
