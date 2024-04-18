import { makeAutoObservable } from 'mobx';
export default class DeviceStore {

    constructor() {
        this._types = []
        this._brands = []
        this._devices = []

        this._selectedDevice = {}
        this._selectedType = {}
        this._selectedBrand = {}
        makeAutoObservable(this)
    }
    setSelectedDevice(device) {
        this._selectedDevice = device
    }
    setSelectedBrand(brand) {
        this._selectedBrand = brand
    }
    setSelectedType(type) {
        this._selectedType = type
    }
    setTypes(types) {
        this._types = types
    }
    setBrands(brands) {
        this._brands = brands
    }
    setDevices(devices) {
        this._devices = devices
    }
    get selectedDevice() {
        return this._selectedDevice
    }
    get selectedBrand() {
        return this._selectedBrand
    }
    get selectedType() {
        return this._selectedType
    }
    get Types() {
        return this._types
    }
    get Brands() {
        return this._brands
    }
    get Devices() {
        return this._devices
    }
};
