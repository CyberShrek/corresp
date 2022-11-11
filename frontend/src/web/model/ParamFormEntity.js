// Only used in the params form

// Abstraction
class ParamFormEntity {
    isValid
}

export class PeriodEntity extends ParamFormEntity {
    date1
    date2
    compareWithLastYear
}

export class CarrierEntity extends ParamFormEntity{
    selected
}

export class DepstinatureEntity extends ParamFormEntity{
    selectedStations
}