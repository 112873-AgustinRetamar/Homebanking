package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.hamcrest.text.CharSequenceLength;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import utils.CardUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import java.util.List;
import java.util.Optional;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest

@AutoConfigureTestDatabase(replace = NONE)

public class RepositoriesTest {



    @Autowired
    LoanRepository loanRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    TransactionRepository transactionRepository;


    @Test
    public void existLoans(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans,is(not(empty())));
    }
    //test para validar que haya un loan 'Personal'
    @Test
    public void existPersonalLoan(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
    }
    @Test
    public void existClient(){
        List<Client> clients = clientRepository.findAll();
        assertThat(clients,is(not(empty())));
    }
    //test para validar que los mails tengan @
    @Test
    public void emailHasAt(){
        List<Client> clients = clientRepository.findAll();
        assertThat(clients, everyItem(hasProperty("email", containsString("@"))));
    }
    @Test
    public void existClientMelba(){
        List<Client> clients = clientRepository.findAll();
        assertThat(clients, hasItem(hasProperty("firstName", is("Melba"))));
    }
    @Test
    public void existCard(){
        List<Card> cards = cardRepository.findAll();
        assertThat(cards,is(not(empty())));
    }
    //test para ver si hay al menos una card GOLD
    @Test
    public void existGoldCard(){
        List<Card> cards = cardRepository.findAll();
        assertThat(cards, hasItem(hasProperty("color", is(CardColor.GOLD))));
    }

    @Test
    public void existTransaction(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions,is(not(empty())));
    }
    //test para que las transaction tengan una account
    @Test
    public void hasAccountTransaction(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions,everyItem(hasProperty("account",is(not(nullValue())))));
    }
    //test para que las cards no tengan number nulo o vacio
    @Test
    public void cardNumberIsCreated(){
        List<Card> cards = cardRepository.findAll();
        assertThat(cards,everyItem(hasProperty("number",is(not(emptyOrNullString())))));
    }
    //test para que las cards no tengan cvv nulo
    @Test
    public void cardCvvIsCreated(){
        List<Card> cards = cardRepository.findAll();
        assertThat(cards,everyItem(hasProperty("cvv",is(not(nullValue())))));
    }
    //test de utilidad para metodo de generate N random en utils
    @Test
    public void generateRandomNWorks(){
        String cardNumber = CardUtils.generateRandomN(6);
        assertThat(cardNumber, CharSequenceLength.hasLength(6));
    }
    //test de utilidad para metodo de generate 3 random en utils
    @Test
    public void generate3RandomWorks(){
        String random3 = CardUtils.generate3Random();
        assertThat(random3,is(not(emptyOrNullString())));
    }
}