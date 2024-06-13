package adeo.leroymerlin.cdp.dao;

import adeo.leroymerlin.cdp.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface EventRepository extends JpaRepository<Event, Long> {
}
