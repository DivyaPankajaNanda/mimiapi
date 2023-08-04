package com.divyapankajananda.mimiapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.divyapankajananda.mimiapi.entity.Icon;
import com.divyapankajananda.mimiapi.exception.ResourceNotFoundException;
import com.divyapankajananda.mimiapi.repository.IconRepository;

@Service
public class IconService {
    @Autowired
    private IconRepository iconRepository;

    public Icon findByIconId(long iconId) {
        Optional<Icon> icon = iconRepository.findByIconId(iconId);
        
        if(!icon.isPresent())
        throw new ResourceNotFoundException("Icon not found.");
        
        return icon.get();
    }

    public List<Icon> findAll() {
        List<Icon> icons = iconRepository.findAll();

        if(icons.isEmpty())
        throw new ResourceNotFoundException("Icons not found.");

        return icons;
    }
}
