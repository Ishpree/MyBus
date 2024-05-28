
import axios from "axios";
import { useNavigate } from "react-router-dom";

export default function BusData(props)
{
    
    let b=[];
    let s=[];
    let d=[];
    const navigate = useNavigate();

    const handleBooking = async (id, event) => {
        event.preventDefault();
        console.log(id);
        await axios
          .get(`http://localhost:8083/api/Dashboard/book/${id}`, {
            withCredentials: true,
          })
          .then((response) => {
            console.log("Bus Data", response.data);
            // setBackendData(null);

            b=(response.data).BookingDTO;
            s=(response.data).BookingDTO.seats;
            d=(response.data).BusData;
            console.log(s);
            console.log(b);
            props.booking(b);
            props.userSeat(s);
            navigate("/Dashboard/Booking");
          });
      };

    return(
        <>
        {props.backendData && (
        <div className="data-table-container">
          <table>
            <thead>
              <tr>
                <th>From</th>
                <th>To</th>
                <th>Time</th>
                <th>BusName</th>
                <th>Price</th>
                <th>Availabe Seats</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {props.backendData.map((dataItem) => (
                <tr key={dataItem.bus_name}>
                  <td>{dataItem.from_location}</td>
                  <td>{dataItem.to_destination}</td>
                  <td>{dataItem.bus_name}</td>
                  <td>{dataItem.time}</td>
                  <td>{dataItem.price}</td>
                  <td>{dataItem.available_seats}</td>
                  <td>
                    <button
                      type="submit"
                      diabled={!(props.backendData)}
                      className="btn btn-primary"
                      onClick={async (event) =>
                        await handleBooking(dataItem.id, event)
                      }
                    >
                      Book
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
      </>

    )
}