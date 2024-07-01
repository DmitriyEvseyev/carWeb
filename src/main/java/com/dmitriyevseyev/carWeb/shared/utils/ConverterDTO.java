package com.dmitriyevseyev.carWeb.shared.utils;

import com.dmitriyevseyev.carWeb.model.Car;
import com.dmitriyevseyev.carWeb.model.CarDealership;
import com.dmitriyevseyev.carWeb.model.dto.CarDTO;
import com.dmitriyevseyev.carWeb.model.dto.CarDealershipDTO;
import com.dmitriyevseyev.carWeb.server.controller.UserController;
import com.dmitriyevseyev.carWeb.server.exceptions.DAOFactoryActionException;

import java.util.ArrayList;
import java.util.List;

public class ConverterDTO {
    private static ConverterDTO instance;

    public ConverterDTO() {
    }

    public static ConverterDTO getInstance() {
        if (instance == null) {
            instance = new ConverterDTO();
        }
        return instance;
    }

    public List<CarDTO> convertCarToCarDTO(List<Car> carList) {
        List<CarDTO> carDTOList = new ArrayList<>();

        for (Car car : carList) {
            CarDTO carDTO = CarDTO.builder().
                    id(car.getId()).
                    idDealer(car.getDealer().getId()).
                    name(car.getName()).
                    date(car.getDate()).
                    color(car.getColor()).
                    isAfterCrash(car.isAfterCrash()).
                    build();
            carDTOList.add(carDTO);
        }


        System.out.println("convertCarToCarDTO - " + carDTOList);

        return carDTOList;
    }

    public List<CarDealershipDTO> convertDealerToDealerDTO(List<CarDealership> dealerList) {
        List<CarDealershipDTO> dealerDTOList = new ArrayList<>();

        for (CarDealership dealer : dealerList) {
            CarDealershipDTO dealerDTO = CarDealershipDTO.builder().
                    id(dealer.getId()).
                    name(dealer.getName()).
                    address(dealer.getAddress()).build();
            dealerDTOList.add(dealerDTO);
        }
        return dealerDTOList;
    }

    public List<CarDealership> convertetDealerDTOToDealer (List<CarDealershipDTO> dealerDTOList) {
        List<CarDealership> dealerList = new ArrayList<>();
        for (CarDealershipDTO dealerDTO : dealerDTOList) {
            CarDealership dealer = CarDealership.builder().
                    id(dealerDTO.getId()).
                    name(dealerDTO.getName()).
                    address(dealerDTO.getAddress()).
                    build();
            dealerList.add(dealer);
        }
        return dealerList;
    }

    public Car converterCarDTOToCar (CarDTO carDTO) {
      Car car =  Car.builder().
                id(carDTO.getId()).
                name(carDTO.getName()).
                date(carDTO.getDate()).
                color(carDTO.getColor()).
                isAfterCrash(carDTO.isAfterCrash()).
                build();
        return car;
    }
}
