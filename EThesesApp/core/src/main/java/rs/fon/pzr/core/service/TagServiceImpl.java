package rs.fon.pzr.core.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rs.fon.pzr.core.exception.InvalidArgumentException;
import rs.fon.pzr.core.service.repository.TagRepository;
import rs.fon.pzr.core.domain.model.thesis.Tag;

import static rs.fon.pzr.core.domain.model.thesis.Tag.*;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Set<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Transactional
    @Override
    public Tag addTag(String value) {
        value = value.toLowerCase().replaceAll("\\s+", "");
        Optional<Tag> tag = tagRepository.findByValue(value);
        if (tag.isPresent()) {
            return tag.get();
        }

        return tagRepository.save(createTag(value));
    }

    @Transactional
    @Override
    public void removeTag(Long id) {
        Tag tag = tagRepository.findOne(id);
        if (tag == null) {
            throw new InvalidArgumentException("Tag sa id-em " + id
                    + " ne postoji u bazi!");
        }
        tagRepository.delete(id);
    }
}
