package com.workintech.library_automation.models.LibraryRelated;


import com.workintech.library_automation.enums.Category;
import com.workintech.library_automation.enums.DeweyField;
import com.workintech.library_automation.interfaces.IDewey;
import com.workintech.library_automation.models.People.Person;

import java.util.*;

public class Library {

    private String name;
    final private Map<String, Map<String, List<Long>>> mapToIdsCategorized;
    final private Map<String, Long> mapFromDeweyToId;
    final private Map<Long, List<Long>> mapIdToBarcodeList;
    final private Map<Long, LibraryReadingMaterial> mapBarcodeToLibraryReadingMaterial;

    final private Map<String, List<Long>> mapPersonToBarcodeList;

    private static long nextBarcode = 0;
    private static long nextId = 0;

    private long generateBarcode() {
        return nextBarcode++;
    }
    private long generateId() {
        return nextId++;
    }

    private double assets;
    final private double feeRenting = 5;

    final private int maxBooksPerPerson = 5;

    public Library(String name) {
        this.name = name;
        this.assets = 100000;
        this.mapToIdsCategorized = new HashMap<>();
        this.mapFromDeweyToId = new HashMap<>();
        this.mapIdToBarcodeList = new HashMap<>();
        this.mapBarcodeToLibraryReadingMaterial = new HashMap<>();
        this.mapPersonToBarcodeList = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Map<String, List<Long>>> getMapToIdsCategorized() {
        return mapToIdsCategorized;
    }

    public void add(ReadingMaterial readingMaterial) {

        final String deweyString = readingMaterial.generateDeweyRepresentation();
        Long id = mapFromDeweyToId.get(deweyString);

        if(id == null) {
            id = generateId();
            mapFromDeweyToId.put(deweyString, id);
        }

        final long barcode = generateBarcode();

        var listBarcodes = mapIdToBarcodeList.get(id);
        if(null == listBarcodes) {

            List<Long> list = new ArrayList<>();
            list.add(barcode);
            mapIdToBarcodeList.put(id, list);
        } else {
            listBarcodes.add(barcode);
        }

        mapBarcodeToLibraryReadingMaterial.put(barcode, new LibraryReadingMaterial(barcode, readingMaterial));

        var deweyMap = IDewey.split(deweyString);
        for(Map.Entry<String, String> entry: deweyMap.entrySet()) {
            String firstLevelKey = entry.getKey();
            String secondLevelKey = entry.getValue();
            Map<String, List<Long>> secondLevelMap = mapToIdsCategorized.get(firstLevelKey);
            if(null == secondLevelMap) {
                Map<String, List<Long>> newMap = new HashMap<>();
                mapToIdsCategorized.put(firstLevelKey, newMap);
                List<Long> newList = new ArrayList<>();
                newMap.put(secondLevelKey, newList);
                newList.add(id);
            } else if (null == secondLevelMap.get(secondLevelKey)) {
                List<Long> newList = new ArrayList<>();
                secondLevelMap.put(secondLevelKey, newList);
                newList.add(id);
            } else {
                List<Long> list = secondLevelMap.get(secondLevelKey);
                if(!list.contains(id))
                    list.add(id);
            }
        }
    }

    public double getFeeRenting() {
        return feeRenting;
    }

    public double receive(LibraryReadingMaterial material) {

        double change = 0;
        material.setAvailable(true);
        if(!material.isDamaged()) {
            change = feeRenting;
            assets -= feeRenting;
        } else {
            material.setDamaged(false);
        }

        return change;
    }

    public LibraryReadingMaterial lend(long barcode, Person person, double fee) {

        LibraryReadingMaterial item = mapBarcodeToLibraryReadingMaterial.get(barcode);

        if(item == null) {

            System.out.println("No reading material registered to barcode number " + barcode);
            return null;
        }

        if(!item.isAvailable()) {

            System.out.println("Reading material registered to barcode number " + barcode + " is not available");
            return null;
        }

        if(fee < feeRenting) {
            System.out.println("Renting fee is " + feeRenting);
            return null;
        }

        String personDeweyRep = person.generateDeweyRepresentation();
        List<Long> personBooks = mapPersonToBarcodeList.get(personDeweyRep);
        if(personBooks == null) {
            List<Long> list = new LinkedList<>();
            list.add(barcode);
            mapPersonToBarcodeList.put(personDeweyRep, list);
        } else if(personBooks.size() >= maxBooksPerPerson) {
            System.out.println(person.getFirstName() + " " + person.getLastName() + " has reached the maximum borrowing limit");
            return null;
        } else {
            personBooks.add(barcode);
        }

        item.setAvailable(false);
        assets += fee;
        return item;
    }


    public void remove(ReadingMaterial readingMaterial) {

        String deweyString = readingMaterial.generateDeweyRepresentation();

        Long id = mapFromDeweyToId.get(deweyString);
        if(id == null) {
            System.out.printf("You cannot remove %s, because it does not exist.", readingMaterial);
            return;
        }
        mapFromDeweyToId.remove(deweyString);

        var listBarcodes = mapIdToBarcodeList.get(id);
        for(long barcode: listBarcodes) {
            mapBarcodeToLibraryReadingMaterial.remove(barcode);
        }
        mapIdToBarcodeList.remove(id);

        var deweyMap = IDewey.split(deweyString);
        for(Map.Entry<String, String> entry: deweyMap.entrySet()) {
            String firstLevelKey = entry.getKey();
            String secondLevelKey = entry.getValue();
            Map<String, List<Long>> secondLevelMap = mapToIdsCategorized.get(firstLevelKey);
//            if(null == secondLevelMap) {
//                System.out.println(firstLevelKey + " " + secondLevelKey + " does not exist");
//                return;
//            }
//            if (null == secondLevelMap.get(secondLevelKey)) {
//                System.out.println(secondLevelKey + " does not exist");
//                return;
//            }

            List<Long> list = secondLevelMap.get(secondLevelKey);
            int idx = list.indexOf(id);
            if(idx == -1) {
                System.out.println(id + " does not exist");
                return;
            }
            list.remove(idx);
            if(list.isEmpty()) {
                secondLevelMap.remove(secondLevelKey);
                if(secondLevelMap.isEmpty()) {
                    mapToIdsCategorized.remove(firstLevelKey);
                }
            }
        }
    }

    public LibraryReadingMaterial query(long barcode) {
        return mapBarcodeToLibraryReadingMaterial.get(barcode);
    }

    public List<LibraryReadingMaterial> queryAll(Person author) {

        return getLibraryReadingMaterials(DeweyField.AUTHOR.name(), author.generateDeweyRepresentation());
    }

    public List<LibraryReadingMaterial> queryAll(String title) {

        return getLibraryReadingMaterials(DeweyField.TITLE.name(), IDewey.generateValue(title));
    }

    public List<LibraryReadingMaterial> queryAll(Category category) {

        return getLibraryReadingMaterials(DeweyField.CATEGORY.name(), category.name());
    }

    private List<LibraryReadingMaterial> getLibraryReadingMaterials(String keyOfMapToIdsCategorized, String deweyField) {

        var secondLevelMap = mapToIdsCategorized.get(keyOfMapToIdsCategorized);
        if(secondLevelMap == null) {
            System.out.printf("Could not found a match for %s\n", keyOfMapToIdsCategorized);
            return null;
        }

        List<Long> listIds = secondLevelMap.get(IDewey.generateValue(deweyField));
        if(listIds == null) {
            System.out.printf("Could not found a match for %s -> %s\n", keyOfMapToIdsCategorized, deweyField);
            return null;
        }

        List<LibraryReadingMaterial> returnList = new ArrayList<>();
        for(Long id: listIds) {

            for(Long barcode : mapIdToBarcodeList.get(id)) {
                returnList.add(mapBarcodeToLibraryReadingMaterial.get(barcode));
            }
        }
        return returnList;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder(mapBarcodeToLibraryReadingMaterial.size() * 100);
        builder.append(name).append(" Library Reading Materails List:\n");

        for (LibraryReadingMaterial item : mapBarcodeToLibraryReadingMaterial.values()) {
            builder.append(item).append("\n");
        }

        return builder.toString();
    }
}