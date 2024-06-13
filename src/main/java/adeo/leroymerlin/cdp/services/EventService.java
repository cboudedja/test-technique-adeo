package adeo.leroymerlin.cdp.services;

import adeo.leroymerlin.cdp.dao.EventRepository;
import adeo.leroymerlin.cdp.exceptions.NotFoundEventException;
import adeo.leroymerlin.cdp.models.Band;
import adeo.leroymerlin.cdp.models.Event;
import adeo.leroymerlin.cdp.models.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getEvents() {
        return eventRepository.findAll();
    }

    public void delete(Long id) throws NotFoundEventException {
        Optional<Event> event  = eventRepository.findById(id);
        if (!event.isPresent()){
            throw new NotFoundEventException("Event not found");
        }
        eventRepository.delete(event.get());
    }

    public void update(Long eventId, Event eventToUpdate) throws NotFoundEventException {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if(!optionalEvent.isPresent()){
            throw new NotFoundEventException("Event not found");
        }
        Event event = optionalEvent.get();
        event.setNbStars(eventToUpdate.getNbStars());
        event.setComment(event.getComment());
        eventRepository.save(event);

    }

    public List<Event> getFilteredEvents(String query) {
        List<Event> events = eventRepository.findAll();
        List<Event> filteredEvents = new ArrayList<>();
        for (Event event : events){
            for (Band band : event.getBands()){
                for (Member member : band.getMembers()){
                    if (member.getName().toLowerCase().contains(query.toLowerCase())){
                        filteredEvents.add(event);
                    }
                }
            }
        }
        return filteredEvents;
    }
}
