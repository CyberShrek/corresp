export class Report {
    rows = []
    // reportNum is used to define which ReportRow class will be used.
    // reportTable is literally table must have header (basic array of column names) and body (rows whose are basic arrays too)
    constructor(reportNum, reportTable) {
        let RowClass
        switch (reportNum) {
            case 1: RowClass = Report1Row; break
            case 2: RowClass = Report2Row; break
            case 4: RowClass = Report4Row; break
            case 5: RowClass = Report5Row; break
            case 6: RowClass = Report6Row; break
            case 7: RowClass = Report7Row; break
            default: throw new Error("Invalid report num: " + reportNum)
        }

        this.rows = reportTableToRows(reportTable, RowClass)
    }
}

// Report 3 is slightly different from Report3: it consists of daily report and monthly report
// whose are converted to the same rows.
export class Report3 {
    dailyRows   = []
    monthlyRows = []

    // dailyReportTable and monthlyReportTable args must have the same structure as in the Report reportTable
    constructor(dailyReportTable, monthlyReportTable) {
        this.dailyRows   = reportTableToRows(dailyReportTable,   DailyReport3Row)
        this.monthlyRows = reportTableToRows(monthlyReportTable, MonthlyReport3Row)
    }
}

class ReportRow {
    // Array is a basic array representing a table row, indexes is an object with table field indexes of this array
    constructor(rowArray, columnIndexes) {
        this.passengers                     = rowArray[columnIndexes.passengers]
        this.passengersProportion           = rowArray[columnIndexes.passengersProportion]
        this.passengersChangedFromLY        = rowArray[columnIndexes.passengersChangedFromLY]
        this.income                         = rowArray[columnIndexes.income]
        this.incomeProportion               = rowArray[columnIndexes.incomeProportion]
        this.incomeChangedFromLY            = rowArray[columnIndexes.incomeChangedFromLY]
        this.incomeRate                     = rowArray[columnIndexes.incomeRate]
        this.incomeRateChangedFromLY        = rowArray[columnIndexes.incomeRateChangedFromLY]
        this.passengerTurnover              = rowArray[columnIndexes.passengerTurnover]
        this.passengerTurnoverProportion    = rowArray[columnIndexes.passengerTurnoverProportion]
        this.passengerTurnoverChangedFromLY = rowArray[columnIndexes.passengerTurnoverChangedFromLY]
    }
}

class Report1Row extends ReportRow{
    constructor(rowArray, columnIndexes) {
        super(rowArray, columnIndexes)
        this.fromCode    = rowArray[columnIndexes.fromCode]
        this.toCode      = rowArray[columnIndexes.toCode]
        this.fromToNames = rowArray[columnIndexes.fromToNames]
        this.trainsCount = rowArray[columnIndexes.trainsCount]
    }
}

class Report2Row extends ReportRow{
    constructor(rowArray, columnIndexes) {
        super(rowArray, columnIndexes)
        this.trainNum = rowArray[columnIndexes.trainNum]
        this.isBranded = rowArray[columnIndexes.isBranded]
        this.categoryName = rowArray[columnIndexes.categoryName]
    }
}

class DailyReport3Row extends ReportRow{
    constructor(rowArray, columnIndexes) {
        super(rowArray, columnIndexes)
        this.day = translateDayToRussian(rowArray[columnIndexes.day])
            + " " + rowArray[columnIndexes.date].substring(8)
    }
}

class MonthlyReport3Row extends ReportRow{
    constructor(rowArray, columnIndexes) {
        super(rowArray, columnIndexes)
        this.month = translateMonthToRussian(rowArray[columnIndexes.month])
             + " " + rowArray[columnIndexes.year]
    }
}

class Report4Row extends ReportRow{
    constructor(rowArray, columnIndexes) {
        super(rowArray, columnIndexes)
        this.carCategory = rowArray[columnIndexes.carCategory]
        this.serviceClass = rowArray[columnIndexes.serviceClass]
    }
}

class Report5Row extends ReportRow{
    constructor(rowArray, columnIndexes) {
        super(rowArray, columnIndexes)
        this.docType = rowArray[columnIndexes.docType]
    }
}

class Report6Row extends ReportRow{
    constructor(rowArray, columnIndexes) {
        super(rowArray, columnIndexes)
        this.benefitGroup = rowArray[columnIndexes.benefitGroup]
        this.benefitName = rowArray[columnIndexes.benefitName]
        this.tariffPlan = rowArray[columnIndexes.tariffPlan]
    }
}

class Report7Row extends ReportRow{
    constructor(rowArray, columnIndexes) {
        super(rowArray, columnIndexes)
        this.genderSign = rowArray[columnIndexes.genderSign]
        this.tariffCode = rowArray[columnIndexes.tariffCode]
        this._ = rowArray[columnIndexes._]
    }
}

function translateDayToRussian(DAY){
    switch (DAY){
        case "MON": return "пн"
        case "TUE": return "пт"
        case "WED": return "ср"
        case "THU": return "чт"
        case "FRI": return "пт"
        case "SAT": return "сб"
        case "SUN": return "вс"
        default: return DAY
    }
}

function translateMonthToRussian(MON){
    switch (MON){
        case "JAN": return "янв"
        case "FEB": return "фев"
        case "MAR": return "мар"
        case "APR": return "апр"
        case "MAY": return "май"
        case "JUN": return "июн"
        case "JUL": return "июл"
        case "AUG": return "авг"
        case "SEP": return "сен"
        case "OCT": return "окт"
        case "NOV": return "ноя"
        case "DEC": return "дек"
        default: return MON
    }
}

function reportTableToRows(reportTable, RowClass){
    const columnIndexes = getColumnIndexesOfHeader(reportTable.header)
    return reportTable.body
        .map(rowArray => new RowClass(rowArray, columnIndexes))
}

// Takes a header row array, find indexes by its values and returns an object having all these indexes as js fields
function getColumnIndexesOfHeader(header){
    const indexes = {}
    for (let i = 0; i < header.length; i++) {
        switch (header[i]){
            // Common:
            case "passengers"                        : indexes.passengers = i; break
            case "passengers_proportion"             : indexes.passengersProportion = i; break
            case "passengers_changed_from_ly"        : indexes.passengersChangedFromLY = i; break
            case "income"                            : indexes.income = i; break
            case "income_proportion"                 : indexes.incomeProportion = i; break
            case "income_changed_from_ly"            : indexes.incomeChangedFromLY = i; break
            case "income_rate"                       : indexes.incomeRate = i; break
            case "income_rate_changed_from_ly"       : indexes.incomeRateChangedFromLY = i; break
            case "passenger_turnover"                : indexes.passengerTurnover = i; break
            case "passenger_turnover_proportion"     : indexes.passengerTurnoverProportion = i; break
            case "passenger_turnover_changed_from_ly": indexes.passengerTurnoverChangedFromLY = i; break

            // Report 1:
            case "from_code"    : indexes.fromCode = i; break
            case "to_code"      : indexes.toCode = i; break
            case "from_to_names": indexes.fromToNames = i; break
            case "trains_count" : indexes.trainsCount = i; break

            // Report 2:
            case "train_num"    : indexes.trainNum = i; break
            case "is_branded"   : indexes.isBranded = i; break
            case "category_name": indexes.categoryName = i; break

            // Report 3:
            case "date" : indexes.date = i; break
            case "day"  : indexes.day = i; break
            case "month": indexes.month = i; break
            case "year" : indexes.year = i; break

            // Report 4:
            case "car_category" : indexes.carCategory = i; break
            case "service_class": indexes.serviceClass = i; break

            // Report 5:
            case "doc_type": indexes.docType = i; break

            // Report 6:
            case "benefit_group": indexes.benefitGroup = i; break
            case "benefit_name" : indexes.benefitName = i; break
            case "tariff_plan"  : indexes.tariffPlan = i; break

            // Report 7:
            case "gender_sign"     : indexes.genderSign = i; break
            case "tariff_code": indexes.tariffCode = i; break
        }
    }
    return indexes
}