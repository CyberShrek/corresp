<script>
    import ReportTemplate from "./templates/ReportTemplate.svelte"
    import SquareLabel from "../common/SquareLabel.svelte"
    import {declineNumeral, filtrate, scrollInto} from "../../utils"
    import {fly} from "svelte/transition"
    import Table from "./templates/table/Table.svelte"
    import Row from "./templates/table/Row.svelte"
    import {createEventDispatcher} from "svelte"
    const dispatch = createEventDispatcher()

    export let inputParams

    let report,
        totalRow,
        filtratedRows = [],
        selectedRows  = [],
        filter,
        filterWidth

    const cleanSelects=() => selectedRows  = []

    $: if (report){
        cleanSelects()

        totalRow = report.rows.find(row => row.fromToNames === "TOTAL")

        filtratedRows = report.rows
            .filter(row => row.fromToNames !== "TOTAL" && filtrate(row.fromToNames, filter))
    }
    $: noRowsSelected = selectedRows.length === 0

    const dispatchGenerateReport=(reportNum, row) => dispatch("generateReport" + reportNum, {
        date1: inputParams.date1,
        date2: inputParams.date2,
        compareWithLastYear: inputParams.compareWithLastYear,
        carrierCode: inputParams.carrierCode,
        fromCodes: row ? row.fromCode : selectedRows.map(row => row.fromCode),
        toCodes:   row ? row.toCode   : selectedRows.map(row => row.toCode)
    })
</script>

<ReportTemplate title="Список корреспонденций пассажиропотоков"
                reportNum={1}
                inputParams={inputParams}
                bind:report>

    <labels transition:fly={{y: 100}}>
        <SquareLabel name={"Пассажир" + declineNumeral(totalRow.passengers, ["", "а", "ов"])}
                     value={totalRow.passengers}
                     image="img/passenger.png"
                     background="#e2f0d9"/>

        <SquareLabel name={`Тысяч${declineNumeral(totalRow.income, ["а", "и", ""])} рублей`}
                     value={totalRow.income}
                     image="img/wallet.png"
                     background="#dae3f3"/>

        <SquareLabel name={`Рубл${declineNumeral(totalRow.incomeRate, ["ь", "я", "ей"])} с пассажира`}
                     value={totalRow.incomeRate}
                     image="img/ruble.png"
                     background="#fbe5d6"/>

        <SquareLabel name={"Пассажиро-километр" + declineNumeral(totalRow.passengerTurnover, ["", "а", "ов"])}
                     value={totalRow.passengerTurnover}
                     image="img/distance.png"
                     background="#fff2cc"/>
    </labels>
    <Table>
        <th slot="before-0" rowspan="2"></th>
        <th slot="before-1" rowspan="2">
            <buttons-block style="width: {filterWidth}px" class:unavailable={noRowsSelected}>
                <button title="Анализ неравномерности"
                        on:click={() => dispatchGenerateReport(3)}><img src="img/graph.png"     alt=""></button>
                <button title="Анализ по типам вагонов"
                        on:click={() => dispatchGenerateReport(4)}><img src="img/train_car.png" alt=""></button>
                <button title="Анализ по виду документа"
                        on:click={() => dispatchGenerateReport(5)}><img src="img/document.png"  alt=""></button>
                <button title="Анализ по характеристикам места"
                        on:click={() => dispatchGenerateReport(7)}><img src="img/seat.png"      alt=""></button>
            </buttons-block>

            <label bind:offsetWidth={filterWidth}>
                <input type="text"
                       bind:value={filter}
                       on:click={(event) => scrollInto(event.target.closest("table"))}
                       placeholder="Корресподенции"></label>
        </th>
        <th slot="after-0"  rowspan="2">Количество поездов, ед.</th>

        <tbody slot="body">
        {#each filtratedRows as row (row.fromToNames)}
            <Row row={row}>
                <td slot="before-0">
                    <input type="checkbox"
                           value={row}
                           bind:group={selectedRows}>
                    </td>
                <td slot="before-1">{row.fromToNames}</td>
                <td slot="after-0">
                    <div class="link"
                         title="Показать отчёт по поездам"
                         on:click={() => dispatchGenerateReport(2, row)}> {row.trainsCount}</div>
                </td>
            </Row>
        {/each}
        </tbody>
    </Table>
</ReportTemplate>

<style>
    labels {
        display: flex;
        justify-content: space-around;
        padding: 10px;
    }
    input[type=checkbox]{transform: scale(1.3)}

    .link {
        cursor: pointer;
        font-weight: bold;
        color: deepskyblue;
        text-decoration: underline;
    }

    th buttons-block{
        display: flex;
        justify-content: space-between;
        margin: 0 auto 10px auto;
    }
    th buttons-block button{
        display: flex;
        justify-content: center;
        align-items: center;
        height: 30px;
        width:  30px;
        padding: 0;
        background: white;
    }
    th buttons-block button img{
        display: flex;
        justify-content: center;
        align-items: center;
        height: 80%;
        width:  80%;
    }
</style>