package org.example.andensemesterproeve_thesemicolons;

import org.example.andensemesterproeve_thesemicolons.application.EventService;
import org.example.andensemesterproeve_thesemicolons.application.ExceptionService;
import org.example.andensemesterproeve_thesemicolons.domain.*;
import org.example.andensemesterproeve_thesemicolons.domain.enums.EventStatus_ENUM;
import org.example.andensemesterproeve_thesemicolons.domain.enums.EventType_ENUM;
import org.example.andensemesterproeve_thesemicolons.domain.enums.Title_ENUM;
import org.example.andensemesterproeve_thesemicolons.domain.interfacesRepo.IEventRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AndensemesterProeveTheSemicolonsApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void newUserHasExpectedValues() {
        User testUser = new User(1, "testUser", "testUser@email.com", Title_ENUM.Admin);
        assertEquals(1, testUser.getId());
        assertEquals("testUser", testUser.getUsername());
        assertEquals("testUser@email.com", testUser.getEmail());
        assertEquals(Title_ENUM.Admin, testUser.getTitle());
    }

    @Test
    void validatingInvalidPasswordThrowsException() {
        User testUser = new User(1, "testUser", "testUser@email.com", Title_ENUM.Admin);
        testUser.setPassword("abc");
        assertThrows(IllegalArgumentException.class,
                testUser::validateValues);
    }

    @Test
    void newDeckHasExpectedValues() {
        Deck deck = new Deck(1, "Mit deck-navn", "Standard");
        assertEquals(1, deck.getId());
        assertEquals("Mit deck-navn", deck.getName());
        assertEquals("Standard", deck.getFormat());
    }

    @Test
    void settingDeckNameToBlankWillSetDefaultValue() {
        Deck deck = new Deck();
        deck.setName("    ");
        assertEquals("Intet navn", deck.getName());
    }

    @Test
    void settingDeckFormatToBlankWillSetDefaultValueNull() {
        Deck deck = new Deck();
        deck.setFormat("   ");
        assertNull(deck.getFormat());
    }

    @Test
    void addingCardToDeckAddsToDeck() {
        Deck deck = new Deck();
        Card card = new Card();
        deck.addCard(card);
        assertEquals(1, deck.getCards().size());
    }
    @Test
    void createNewEventWithParameters(){
        Event event = new Event(1, "fun", EventType_ENUM.Casual, "Commander",4, LocalDate.now(), LocalTime.of(11,23,43), EventStatus_ENUM.Lukket_for_tilmelding);
        assertEquals(1, event.getId());
        assertEquals("fun", event.getName());
        assertEquals(EventType_ENUM.Casual, event.getType());
        assertEquals("Commander", event.getFormat());
        assertEquals(4, event.getMaxPlayers());
        assertEquals(LocalDate.now(), event.getDate());
        assertEquals(LocalTime.of(11,23,43), event.getTime());
        assertEquals(EventStatus_ENUM.Lukket_for_tilmelding, event.getEventStatus());
    }

    @Test
    void ShowAllMyArrangedEvents(){
        IEventRepository mockRepository = mock(IEventRepository.class);
        ExceptionService mockExceptionService = mock(ExceptionService.class);
        EventService eventService = new EventService(mockRepository, mockExceptionService);
        User testUser = new User(1, "testUser", "testUser@email.com", Title_ENUM.Admin);
        Event testEvent = new Event(1, "fun", EventType_ENUM.Casual, "Commander",4, LocalDate.now(), LocalTime.of(11,23,43), EventStatus_ENUM.Aaben_for_tilmelding);
        List<Event> expectedList = List.of(testEvent);

        when(mockRepository.findAllMyArrangedEvents(testUser.getId())).thenReturn(expectedList);
        eventService.getAllMyArrangedEvents(testUser.getId());

        List<Event> actualList = eventService.getAllMyArrangedEvents(testUser.getId());
        assertEquals(expectedList, actualList);
    }

    @Test
    void signUpForEventAndTestIfEventStatusIsUpdated() {
        IEventRepository mockRepository = mock(IEventRepository.class);
        ExceptionService mockExceptionService = mock(ExceptionService.class);
        EventService eventService = new EventService(mockRepository, mockExceptionService);
        User testUser = new User(1, "testUser", "testUser@email.com", Title_ENUM.Admin);
        Event testEvent = new Event(1, "fun", EventType_ENUM.Casual, "Commander", 1, LocalDate.now(), LocalTime.of(11, 23, 43), EventStatus_ENUM.Aaben_for_tilmelding);

        when(mockRepository.getEventById(testEvent.getId())).thenReturn(testEvent);
        when(mockRepository.getNumberOfParticipantsFromId(testEvent.getId())).thenReturn(0);
        when(mockRepository.userIsAlreadySignedUp(testUser.getId(), testEvent.getId())).thenReturn(false);

        boolean result = eventService.signUpForEvent(testUser.getId(), testEvent.getId());
        assertTrue(result);

        verify(mockRepository, times(1)).updateEventStatus(testEvent.getId(), EventStatus_ENUM.Fuldt_booket.name());
    }

}
