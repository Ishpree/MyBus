import { useEffect } from "react";
export default function Booked(props)
{


  // const newBookingList=[...props.listBookings];
  //     newBookingList.push(props.booked);
  //     props.allBookings(newBookingList);
  //     console.log(newBookingList);
  //     sessionStorage.setItem("booking",book)
  
  let displaySeats=[];
  displaySeats=props.seatNos;
  console.log(displaySeats);
    return(
        <div>
            {props.booked && (
        <div className="data-table-container">
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
              {
                <tr key={props.booked.bus_name}>
                  <td>{props.booked.from_location}</td>
                  <td>{props.booked.to_destination}</td>
                  <td>{props.booked.bus_name}</td>
                  <td>{props.booked.noOfPeople}</td>
                  <td>{props.booked.total}</td>
                  {displaySeats.map((dataItem, index)=>
                  <td>{dataItem.my_seat}</td>)}
                  
                </tr>
              }
            </tbody>
          </table>
        </div>
      )}
      </div>)
}