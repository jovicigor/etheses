package rs.fon.pzr.core.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rs.fon.pzr.core.exception.InvalidArgumentException;
import rs.fon.pzr.persistence.model.TagEntity;
import rs.fon.pzr.persistence.repository.TagRepository;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public TagEntity getTag(Long id) {
        return tagRepository.findOne(id);
    }

    @Override
    public TagEntity getTagByValue(String value) {
        return tagRepository.findByValue(value);
    }

    @Override
    public Set<TagEntity> getAllTags() {
        return tagRepository.findAll();
    }

    @Transactional
    @Override
    public TagEntity addTag(String value) {
        value = value.toLowerCase();
        value = value.replaceAll("\\s+", "");
        TagEntity tag = tagRepository.findByValue(value);
        if (tag != null) {
            return tag;
        }
        tag = new TagEntity();
        tag.setValue(value);
        return tagRepository.save(tag);
    }

    @Transactional
    @Override
    public void removeTag(Long id) {
        TagEntity tag = tagRepository.findOne(id);
        if (tag == null) {
            throw new InvalidArgumentException("TagEntity sa id-em " + id
                    + " ne postoji u bazi!");
        }
        tagRepository.delete(id);
    }
}
