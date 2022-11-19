package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.net.PasswordAuthentication;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	//llama al bean de passwordEnconder  del configurations WebAuthentication
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//	@Bean
//	public CommandLineRunner initData(ClientRepository cRepository, AccountRepository aRepository, TransactionRepository tRepository, LoanRepository lRepository, ClientLoanRepository clientLoanRepository,CardRepository cardRepository){
//		return (args) -> {
//
//
//
//			Client client1 = new Client("Melba", "Lorenzo","melba@mindhub.com",passwordEncoder.encode("123"));
//			cRepository.save(client1);
//			Client client2 = new Client("Agustin", "Retamar","retamaragustin@mindhub.com",passwordEncoder.encode("abc234"));
//			cRepository.save(client2);
//
//			Account account1 =new Account("VIN001", LocalDateTime.now(),5000,client1);
//			aRepository.save(account1);
//			Account account2 =new Account("VIN002", LocalDateTime.now().plusDays(1),7500,client1);
//			aRepository.save(account2);
//			Account account3 =new Account("1236a", LocalDateTime.now().plusDays(2),8500,client2);
//			aRepository.save(account3);
//			Account account4 =new Account("1237b", LocalDateTime.now(),3500,client2);
//			aRepository.save(account4);
//
//			Transaction	transaction1=new Transaction("venta",LocalDateTime.now(),500, TransactionType.CREDIT,account1);
//			tRepository.save(transaction1);
//			Transaction	transaction2=new Transaction("cobro",LocalDateTime.now(),600, TransactionType.CREDIT,account1);
//			tRepository.save(transaction2);
//			Transaction	transaction3=new Transaction("venta",LocalDateTime.now(),300, TransactionType.CREDIT,account2);
//			tRepository.save(transaction3);
//			Transaction	transaction4=new Transaction("cobro",LocalDateTime.now(),800, TransactionType.CREDIT,account3);
//			tRepository.save(transaction4);
//			Transaction	transaction5=new Transaction("compra",LocalDateTime.now(),-(200), TransactionType.DEBIT,account3);
//			tRepository.save(transaction5);
//			Transaction	transaction6=new Transaction("compra",LocalDateTime.now(),-(150), TransactionType.DEBIT,account4);
//			tRepository.save(transaction6);
//
//			Loan loan1=new Loan("Hipotecario",500000, List.of(12,24,36,48,60));
//			lRepository.save(loan1);
//			Loan loan2=new Loan("Personal",500000, List.of(6,12,24));
//			lRepository.save(loan2);
//			Loan loan3=new Loan("Automotriz",500000, List.of(6,12,24,36));
//			lRepository.save(loan3);
//
//			ClientLoan clientLoan1=new ClientLoan(60,400000,client1,loan1);
//			clientLoanRepository.save(clientLoan1);
//			ClientLoan clientLoan2=new ClientLoan(12,50000,client1,loan2);
//			clientLoanRepository.save(clientLoan2);
//			ClientLoan clientLoan3=new ClientLoan(24,100000,client2,loan2);
//			clientLoanRepository.save(clientLoan3);
//			ClientLoan clientLoan4=new ClientLoan(36,200000,client2,loan3);
//			clientLoanRepository.save(clientLoan4);
//
//			Card card1=new Card("1660-3030-2020-4040","123",client1.getFirstName()+" "+client1.getLastName(),LocalDate.now(),LocalDate.now().plusYears(5),CardType.DEBIT,CardColor.GOLD,client1);
//			cardRepository.save(card1);
//			Card card2=new Card("2030-3030-2020-5050","456",client1.getFirstName()+" "+client1.getLastName(), LocalDate.now(),LocalDate.now().plusYears(5),CardType.CREDIT,CardColor.TITANIUM,client1);
//			cardRepository.save(card2);
//			Card card3=new Card("1010-2020-3030-4040","888",client2.getFirstName()+" "+client2.getLastName(),LocalDate.now(),LocalDate.now().plusYears(5),CardType.CREDIT,CardColor.SILVER,client2);
//			cardRepository.save(card3);
//
//		};
//	}
}
