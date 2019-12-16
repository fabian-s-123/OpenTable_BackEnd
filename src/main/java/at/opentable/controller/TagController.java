package at.opentable.controller;

import at.opentable.entity.Tag;
import at.opentable.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class TagController {

    @Autowired
    private TagRepository tagRepository;

    public boolean createTag (Tag tag) {
        this.tagRepository.save(tag);
        return true;
    }

    public Iterable<Tag> findAll() {
        return this.tagRepository.findAll();
    }

    public Optional getTAg(int id) {
        return this.tagRepository.findById(id);
    }

    public Optional updateTag(Tag tag) {
        if (getTAg(tag.getId()).isPresent()) {
            this.tagRepository.save(tag);
            return Optional.of(getTAg(tag.getId()));
        }
        return Optional.empty();
    }

    public boolean deleteTag (int id) {
        if (getTAg(id).isPresent()) {

            this.tagRepository.deleteById(id);
            return true;
        } return false;
    }
}
