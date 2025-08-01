package edu.mx.unsis.unsiSmile.controller.addresses;

import edu.mx.unsis.unsiSmile.dtos.request.addresses.AddressRequest;
import edu.mx.unsis.unsiSmile.dtos.response.addresses.AddressResponse;
import edu.mx.unsis.unsiSmile.service.addresses.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unsismile/api/v1/addresses")
@Validated
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<AddressResponse> createAddress(@Valid @RequestBody AddressRequest addressRequest) {
        AddressResponse createdAddress = addressService.createAddress(addressRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAddress);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> getAddressById(@PathVariable Long id) {
        AddressResponse addressResponse = addressService.getAddressById(id);
        return ResponseEntity.ok(addressResponse);
    }

    @GetMapping("/street-number/{streetNumber}")
    public ResponseEntity<List<AddressResponse>> getAddressesByStreetNumber(@PathVariable String streetNumber) {
        List<AddressResponse> addressResponses = addressService.getAddressesByStreetNumber(streetNumber);
        return ResponseEntity.ok(addressResponses);
    }

    @GetMapping("/interior-number/{interiorNumber}")
    public ResponseEntity<List<AddressResponse>> getAddressesByInteriorNumber(@PathVariable String interiorNumber) {
        List<AddressResponse> addressResponses = addressService.getAddressesByInteriorNumber(interiorNumber);
        return ResponseEntity.ok(addressResponses);
    }

    @GetMapping("/housing/{housingId}")
    public ResponseEntity<List<AddressResponse>> getAddressesByHousing(@PathVariable String housingId) {
        List<AddressResponse> addressResponses = addressService.getAddressesByHousing(housingId);
        return ResponseEntity.ok(addressResponses);
    }

    @GetMapping("/street/{streetId}")
    public ResponseEntity<List<AddressResponse>> getAddressesByStreet(@PathVariable Long streetId) {
        List<AddressResponse> addressResponses = addressService.getAddressesByStreet(streetId);
        return ResponseEntity.ok(addressResponses);
    }

    @GetMapping
    public ResponseEntity<List<AddressResponse>> getAllAddresses() {
        List<AddressResponse> allAddresses = addressService.getAllAddresses();
        return ResponseEntity.ok(allAddresses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponse> updateAddress(@PathVariable Long id,@Valid @RequestBody AddressRequest updatedAddressRequest) {
        AddressResponse updatedAddress = addressService.updateAddress(id, updatedAddressRequest);
        return ResponseEntity.ok(updatedAddress);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddressById(@PathVariable Long id) {
        addressService.deleteAddressById(id);
        return ResponseEntity.noContent().build();
    }
}