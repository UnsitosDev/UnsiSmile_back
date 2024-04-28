package edu.mx.unsis.unsiSmile.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.mx.unsis.unsiSmile.dtos.response.addresses.AddressResponse;
import edu.mx.unsis.unsiSmile.model.HousingModel;
import edu.mx.unsis.unsiSmile.model.StreetModel;
import edu.mx.unsis.unsiSmile.service.addresses.AddressService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/unsismile/api/v1/addresses/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> getAddressById(@Valid @PathVariable Long id) {
        AddressResponse addressResponse = addressService.getAddressById(id);
        return ResponseEntity.ok(addressResponse);
    }

    @GetMapping("/street-number/{streetNumber}")
    public ResponseEntity<List<AddressResponse>> getAddressesByStreetNumber(@Valid @PathVariable String streetNumber) {
        List<AddressResponse> addressResponses = addressService.getAddressesByStreetNumber(streetNumber);
        return ResponseEntity.ok(addressResponses);
    }

    @GetMapping("/interior-number/{interiorNumber}")
    public ResponseEntity<List<AddressResponse>> getAddressesByInteriorNumber(@Valid @PathVariable String interiorNumber) {
        List<AddressResponse> addressResponses = addressService.getAddressesByInteriorNumber(interiorNumber);
        return ResponseEntity.ok(addressResponses);
    }

    @GetMapping("/housing/{housingId}")
    public ResponseEntity<List<AddressResponse>> getAddressesByHousing(@Valid @PathVariable String housingId) {
        // Assuming you have a method to get the HousingModel by ID
        HousingModel housing = getHousingById(housingId);
        List<AddressResponse> addressResponses = addressService.getAddressesByHousing(housing);
        return ResponseEntity.ok(addressResponses);
    }

    @GetMapping("/street/{streetId}")
    public ResponseEntity<List<AddressResponse>> getAddressesByStreet(@Valid @PathVariable String streetId) {
        // Assuming you have a method to get the StreetModel by ID
        StreetModel street = getStreetById(streetId);
        List<AddressResponse> addressResponses = addressService.getAddressesByStreet(street);
        return ResponseEntity.ok(addressResponses);
    }

    @GetMapping
    public ResponseEntity<List<AddressResponse>> getAllAddresses() {
        List<AddressResponse> allAddresses = addressService.getAllAddresses();
        return ResponseEntity.ok(allAddresses);
    }

    private HousingModel getHousingById(String housingId) {
        // Implement the logic to fetch the HousingModel by ID
        // This is just a placeholder, you need to provide the actual implementation
        return new HousingModel();
    }

    private StreetModel getStreetById(String streetId) {
        // Implement the logic to fetch the StreetModel by ID
        // This is just a placeholder, you need to provide the actual implementation
        return new StreetModel();
    }
}