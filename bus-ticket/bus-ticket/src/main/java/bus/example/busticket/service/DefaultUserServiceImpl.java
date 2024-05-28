package bus.example.busticket.service;

import bus.example.busticket.dto.BookingsDTO;
import bus.example.busticket.dto.RegisteredUserDTO;
import bus.example.busticket.dto.ReservationDTO;
import bus.example.busticket.model.*;
import bus.example.busticket.repository.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DefaultUserServiceImpl implements DefaultUserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BusDataRepository busDataRepository;

    @Autowired
    private BookingsRepository bookingsRepository;

    @Autowired
    private SeatNoRepository seatNoRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        System.out.println(user.getEmail());
        if(user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRole()));
    }

    //GrantedAuthority is an interface in SpringSecurity that represents an authority
    //granted to an Authentication object. When a user logs in, their set of Granted Authority
    //determines what they are allowed to do within the application
    //The roles that are allocated to a registered user are mapped to authorities that
    //spring security can use to make authoritative decisions
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
        if (roles == null || roles.isEmpty()) {
            // Assign default authority (e.g., "ROLE_USER") or throw an exception
            return Collections.singleton(new SimpleGrantedAuthority("USER")); // Example default role
        }
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
    }


    @Override
    public User save(RegisteredUserDTO registeredUserDTO)
    {
         Role role = roleRepository.findByRole("USER");

             User user = new User();

             user.setEmail(registeredUserDTO.getEmail());
             user.setName(registeredUserDTO.getName());
             user.setPassword(passwordEncoder.encode(registeredUserDTO.getPassword()));
             user.setRole(role);
             return userRepository.save(user);


    }

    @Override
   public List<BusData> findBus(ReservationDTO reservationDTO)
    {
        return busDataRepository.findByToFromAndDate(reservationDTO.getTo_destination(), reservationDTO.getFrom_location(), reservationDTO.getFilter_date());

    }

    @Override
    public UserBookings addBookings(BookingsDTO bookingsDto, String username, BusData busData)
    {
        UserBookings userBookings = new UserBookings();
        User user = userRepository.findByEmail(username);
        userBookings.setUser_id(user.getId());
        userBookings.setBus_name(bookingsDto.getBus_name());
        userBookings.setFilter_date(bookingsDto.getFilter_date());
        userBookings.setFrom_location(bookingsDto.getFrom_location());
        userBookings.setTo_destination(bookingsDto.getTo_destination());
        userBookings.setTime(bookingsDto.getTime());
        userBookings.setNo_of_people(bookingsDto.getNoOfPeople());
        userBookings.setTotal(bookingsDto.getTotal());
        userBookings.setSeat_nos(bookingsDto.getSeat_nos());
        userBookings.setBus_id(bookingsDto.getId());
        userBookings.setTripStatus(true);
        System.out.println(userBookings);
        System.out.println(bookingsDto.getId());
        return bookingsRepository.save(userBookings);
    }

    public void generatePdf(BookingsDTO bookingDTO, User user) throws IOException {

        float MARGIN = 20; // Margin from page edges
        float LINE_SPACING = 5;
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        doc.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(doc, page);

        // Add content to the PDF (replace with your layout and formatting)
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        float pageHeight = page.getMediaBox().getHeight();
        contentStream.newLineAtOffset(20, pageHeight - 20);
        contentStream.showText("Ticket Details");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 10);
        float yPosition = page.getMediaBox().getHeight() - MARGIN; // Start from top

        contentStream.newLineAtOffset(MARGIN, -LINE_SPACING);
        contentStream.showText("Journey Data and Time\n");
        contentStream.showText(bookingDTO.getFilter_date());
        contentStream.showText(bookingDTO.getTime());
        contentStream.newLineAtOffset(0, -LINE_SPACING);
        contentStream.setLineWidth(1);
        contentStream.moveTo(MARGIN, yPosition);
        contentStream.lineTo(page.getMediaBox().getWidth() - MARGIN, yPosition);
        contentStream.stroke();
        contentStream.newLineAtOffset(0, -LINE_SPACING);
        contentStream.showText("Travels\n");
        contentStream.showText(bookingDTO.getBus_name());
        contentStream.newLineAtOffset(0, -LINE_SPACING);
        contentStream.showText("Amount Paid\n");
        contentStream.showText(String.valueOf(bookingDTO.getTotal()));
        contentStream.newLineAtOffset(0, -LINE_SPACING);
        contentStream.setLineWidth(1); // Set line width (adjust as needed)
        contentStream.moveTo(MARGIN, yPosition);
        contentStream.lineTo(page.getMediaBox().getWidth() - MARGIN, yPosition);
        contentStream.stroke();
        contentStream.newLineAtOffset(0, -LINE_SPACING);
        contentStream.showText("Boarding Point\n");
        contentStream.showText(bookingDTO.getFrom_location());
        contentStream.newLineAtOffset(0, -LINE_SPACING);
        contentStream.showText("Dropping Point\n");
        contentStream.showText(bookingDTO.getTo_destination());
        contentStream.newLineAtOffset(0, -LINE_SPACING);
        contentStream.setLineWidth(1); // Set line width (adjust as needed)
        contentStream.moveTo(MARGIN, yPosition);
        contentStream.lineTo(page.getMediaBox().getWidth() - MARGIN, yPosition);
        contentStream.stroke();
        contentStream.newLineAtOffset(0, -LINE_SPACING);
        contentStream.showText("Passenger Details\n");
        contentStream.showText(user.getName() + " and " + (bookingDTO.getNoOfPeople() - 1) + " others");
        contentStream.newLineAtOffset(5, 0);
        contentStream.endText();
        contentStream.close();
        doc.close();
    }



    }




















