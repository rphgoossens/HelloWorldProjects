package nl.whitehorses.hellobeer.web.rest;

import com.codahale.metrics.annotation.Timed;
import nl.whitehorses.hellobeer.domain.ItemStockLevel;

import nl.whitehorses.hellobeer.repository.ItemStockLevelRepository;
import nl.whitehorses.hellobeer.web.rest.errors.BadRequestAlertException;
import nl.whitehorses.hellobeer.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ItemStockLevel.
 */
@RestController
@RequestMapping("/api")
public class ItemStockLevelResource {

    private final Logger log = LoggerFactory.getLogger(ItemStockLevelResource.class);

    private static final String ENTITY_NAME = "itemStockLevel";

    private final ItemStockLevelRepository itemStockLevelRepository;

    public ItemStockLevelResource(ItemStockLevelRepository itemStockLevelRepository) {
        this.itemStockLevelRepository = itemStockLevelRepository;
    }

    /**
     * POST  /item-stock-levels : Create a new itemStockLevel.
     *
     * @param itemStockLevel the itemStockLevel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new itemStockLevel, or with status 400 (Bad Request) if the itemStockLevel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/item-stock-levels")
    @Timed
    public ResponseEntity<ItemStockLevel> createItemStockLevel(@Valid @RequestBody ItemStockLevel itemStockLevel) throws URISyntaxException {
        log.debug("REST request to save ItemStockLevel : {}", itemStockLevel);
        if (itemStockLevel.getId() != null) {
            throw new BadRequestAlertException("A new itemStockLevel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemStockLevel result = itemStockLevelRepository.save(itemStockLevel);
        return ResponseEntity.created(new URI("/api/item-stock-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /item-stock-levels : Updates an existing itemStockLevel.
     *
     * @param itemStockLevel the itemStockLevel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated itemStockLevel,
     * or with status 400 (Bad Request) if the itemStockLevel is not valid,
     * or with status 500 (Internal Server Error) if the itemStockLevel couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/item-stock-levels")
    @Timed
    public ResponseEntity<ItemStockLevel> updateItemStockLevel(@Valid @RequestBody ItemStockLevel itemStockLevel) throws URISyntaxException {
        log.debug("REST request to update ItemStockLevel : {}", itemStockLevel);
        if (itemStockLevel.getId() == null) {
            return createItemStockLevel(itemStockLevel);
        }
        ItemStockLevel result = itemStockLevelRepository.save(itemStockLevel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, itemStockLevel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /item-stock-levels : get all the itemStockLevels.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of itemStockLevels in body
     */
    @GetMapping("/item-stock-levels")
    @Timed
    public List<ItemStockLevel> getAllItemStockLevels() {
        log.debug("REST request to get all ItemStockLevels");
        return itemStockLevelRepository.findAll();
        }

    /**
     * GET  /item-stock-levels/:id : get the "id" itemStockLevel.
     *
     * @param id the id of the itemStockLevel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the itemStockLevel, or with status 404 (Not Found)
     */
    @GetMapping("/item-stock-levels/{id}")
    @Timed
    public ResponseEntity<ItemStockLevel> getItemStockLevel(@PathVariable Long id) {
        log.debug("REST request to get ItemStockLevel : {}", id);
        ItemStockLevel itemStockLevel = itemStockLevelRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(itemStockLevel));
    }

    /**
     * DELETE  /item-stock-levels/:id : delete the "id" itemStockLevel.
     *
     * @param id the id of the itemStockLevel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/item-stock-levels/{id}")
    @Timed
    public ResponseEntity<Void> deleteItemStockLevel(@PathVariable Long id) {
        log.debug("REST request to delete ItemStockLevel : {}", id);
        itemStockLevelRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
