package fr._42.spring.services;

import fr._42.spring.models.Hall;
import fr._42.spring.repositories.HallsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HallsService {
    private final HallsRepository hallsRepository;

    @Autowired
    public HallsService(HallsRepository hallsRepository) {
        this.hallsRepository = hallsRepository;
    }

    public Hall createHall(String serialNumber, int seats) {
        Hall hall = new Hall(null, serialNumber, seats);
        if (hallsRepository.existsBySerialNumber(serialNumber)) {
            throw new IllegalArgumentException("Hall already exists");
        }
        return hallsRepository.save(hall);
    }

    public List<Hall> getHalls() {
        return hallsRepository.findAll();
    }

    public Hall getHallById(Long id) {
        return hallsRepository.findById(id).orElse(null);
    }
}
