import axios
 from "axios";
export default function AllBookings(props)
    {

        let arr=[];
        arr=props.listBookings;

       const handleCancel=async(dataItem, event)=>
        {
          let s=[];
          s=dataItem.seats;
          let se=[];
          se=dataItem.seat_nos;
          for(let j=0;j<se.length;j++)
          {
          for(let i=0;i<20;i++)
          {
              if(se[j].my_seat==s[i].seatKey.seat_no)
              {
                s[i].availability=true;
                break;
              }

          }
        }
        console.log(s);
          event.preventDefault();
            const cancelledBooking={
               id:dataItem.id,
               bus_id:dataItem.bus_id,
               filter_data:dataItem.filter_date,
               from_location:dataItem.from_location,
               to_destination:dataItem.to_destination,
               tripStatus:dataItem.tripStatus,
               price:dataItem.price,
               bus_name:dataItem.bus_name,
               time:dataItem.time,
               noOfPeople:dataItem.noOfPeople,
               total:dataItem.total,
               seats:dataItem.seats,
               available_seats:dataItem.available_seats,
               seat_nos:dataItem.seat_nos 
            }
            console.log(cancelledBooking);
            await axios.post("http://localhost:8083/api/MyBooking/cancel", cancelledBooking,  {withCredentials: true}).
            then((response)=>{console.log("Cancellation Successfull")})
        }

        return(
            <>
        {props.listBookings && (<div className="data-table-container">
        <table>
          <thead>
            <tr>
              <th>From</th>
              <th>To</th>
              <th>Time</th>
              <th>BusName</th>
              <th>No of People</th>
              <th>Total Amount</th>
              <th>Seat Nos</th>
            </tr>
          </thead>
          <tbody>
              {arr.map((dataItem) => (
                <tr key={dataItem.seat_nos}>
                  <td>{dataItem.from_location}</td>
                  <td>{dataItem.to_destination}</td>
                  <td>{dataItem.time}</td>
                  <td>{dataItem.bus_name}</td>
                  <td>{dataItem.noOfPeople}</td>
                  <td>{dataItem.total}</td>
                  {dataItem.seat_nos.map((i, index)=>
                  <td>{i.my_seat}</td>)}
                  <td><button type="submit" onClick={async (event) =>
                        await handleCancel(dataItem, event)}>Cancel</button></td>
           <td><button type="submit">Download</button></td>
           </tr>     
              ))}
              </tbody>
          </table>
        </div>)}
        </>
        
    )
    }

