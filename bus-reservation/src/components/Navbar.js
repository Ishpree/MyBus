import { Link } from "react-router-dom"
import { useNavigate } from "react-router-dom";
import axios
 from "axios";


export default function Navbar(props)
{
  const navigate =useNavigate();

  const handleAllBookings = (event)=>
  {
    event.preventDefault();
     axios.get('http://localhost:8083/api/MyBooking', {withCredentials:true}).then((response)=>{
      console.log(response.data);
      props.allBookings(response.data.MyBookingDTO);
      navigate('/AllBookings');})
    .catch((error)=>
    {
      console.error('No Bookings found');
    });
  };
  
    return(
        <nav className="navbar navbar-expand-lg navbar-dark" style={{height:'90px',backgroundColor:'#9DC183'}}>
  <div className="container-fluid">
    <Link className="navbar-brand" to="/Registration" style={{color:'#333333',marginleft:'50px',fontSize:'20px'}}>QuickBus</Link>
    <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span className="navbar-toggler-icon"></span>
    </button>
    <div className="collapse navbar-collapse" id="navbarSupportedContent">
    {/* <Link className="navbar-brand" to="/AllBookings">My Bookings</Link> */}
    <button className="btn btn-outline-success my-2 my-sm-0" style={{color:'#333333',border:'0',fontSize:'20px'}} type="button" onClick={handleAllBookings}><b>My Bookings</b></button> 
    {/* <Link className="navbar-brand" to="/Registration" style={{color:'#333333',marginleft:'50px',fontSize:'20px'}}>Sign up</Link> */}
    <Link className="navbar-brand" to="/Login" style={{color:'#333333',marginleft:'50px',fontSize:'20px', textAlign:'right'}}>Login</Link>
  </div>
  </div>
</nav>
    )
}