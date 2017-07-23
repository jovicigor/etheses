package rs.fon.pzr.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rs.fon.pzr.core.exception.InvalidArgumentException;
import rs.fon.pzr.persistence.model.Keyword;
import rs.fon.pzr.core.service.KeywordService;
import rs.fon.pzr.rest.model.request.BannedKeywordsRequest;
import rs.fon.pzr.rest.model.request.KeywordRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/keywords")
public class KeywordResource {

    private final KeywordService keywordService;

    @Autowired
    public KeywordResource(KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    // READ
    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    Set<Keyword> getKeywords() {
        return keywordService.getAllKeywords();
    }

    //CREATE
    @RequestMapping(method = RequestMethod.POST)
    public
    @ResponseBody
    List<Keyword> addBannedKeywords(@RequestBody BannedKeywordsRequest bannedKeywords) {
        List<Keyword> keywords = new ArrayList<>();
        for (String keywordValue : bannedKeywords.getKeywords()) {
            keywords.add(keywordService.addBannedKeyword(keywordValue));
        }
        return keywords;
    }

    //UPDATE
    @RequestMapping(method = RequestMethod.PUT, value = "/{keywordID}")
    public
    @ResponseBody
    Keyword updateKeywordBannedStatus(@PathVariable("keywordID") Long keywordID, @RequestBody KeywordRequest keywordRequest) {
        Keyword existingKeyword = keywordService.getKeyword(keywordID);
        if (existingKeyword == null) {
            throw new InvalidArgumentException("Ključna reč sa id-em: " + keywordID + " ne postoji u bazi!");
        }
        existingKeyword.setBanned(keywordRequest.getBanned());
        return keywordService.updateKeyword(existingKeyword);
    }

    // DELETE
    @RequestMapping(method = RequestMethod.DELETE, value = "/{keywordID}")
    public void deleteKeyword(@PathVariable("keywordID") Long keywordID) {
        keywordService.removeKeyword(keywordID);
    }
}
