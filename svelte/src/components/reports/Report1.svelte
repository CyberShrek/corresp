<script>
    import ReportTemplate from "./ReportTemplate.svelte"
    import SquareLabel from "../common/SquareLabel.svelte"
    import {httpClient} from "../../web/httpClient"
    import {declineNumeral} from "../../utils"

    export let inputParams

    let rows = [{
        fromTo: undefined,
        passengers:         { amount: undefined, changedFromLY: undefined, proportion: undefined },
        income:             { amount: undefined, changedFromLY: undefined, proportion: undefined },
        incomeRate:         { amount: undefined, changedFromLY: undefined },
        passengerTurnover:  { amount: undefined, changedFromLY: undefined, proportion: undefined },
        trainsCount: undefined
    }]

    $: promise = inputParams &&
        httpClient.createReport1ByParams(inputParams)
            .then(report => rows = report)

    $: total = rows.find(row => row.fromTo = "Total")


    function getColorByValue(value){
        if (value === 0) return "black"
        if (value > 0) return "limegreen"
        if (value < 0) return "orangered"
    }
</script>

<ReportTemplate title="Список корреспонденций пассажиропотоков">
    <labels>
        <SquareLabel name={"Пассажир" + declineNumeral(total.passengers.amount, ["", "а", "ов"])}
                     value={total.passengers.amount} img="img/passenger.png" background="#e2f0d9"/>

        <SquareLabel name={`Тысяч${declineNumeral(total.income.amount, ["а", "и", ""])} рублей`}
                     value={total.income.amount} img="img/wallet.png"    background="#dae3f3"/>

        <SquareLabel name={`Рубл${declineNumeral(total.incomeRate.amount, ["ь", "я", "ей"])} с пассажира`}
                     value={total.incomeRate.amount} img="img/ruble.png"     background="#fbe5d6"/>

        <SquareLabel name={"Пассажиро-километр" + declineNumeral(total.passengerTurnover.amount, ["", "а", "ов"])}
                     value={total.passengerTurnover.amount} img="img/distance.png"  background="#fff2cc"/>
    </labels>
    <table>
        <thead>
            <tr>
                <th rowspan="2"></th>
                <th rowspan="2">Корресподенция пассажиропотоков</th>
                <th colspan="3">Количество пассажиров, пасс.</th>
                <th colspan="3">Доходные поступления, тыс.руб.</th>
                <th colspan="2">Доходная ставка, руб./пасс.</th>
                <th colspan="3">Пассажирооборот, пасс./км</th>
                <th rowspan="2">Количество поездов, ед.</th>
            </tr>
            <tr>
                <th>Отчётный период</th>
                <th>Рост/падение, %</th>
                <th>Доля в общем объёме, %</th>
                <th>Отчётный период</th>
                <th>Рост/падение, %</th>
                <th>Доля в общем объёме, %</th>
                <th>Отчётный период</th>
                <th>Рост/падение, %</th>
                <th>Отчётный период</th>
                <th>Рост/падение, %</th>
                <th>Доля в общем объёме, %</th>
            </tr>
        </thead>

        <tbody>
        {#each rows as row}
            {#if row.fromTo && row.fromTo !== "Total"}
                <tr>
                    <td><label><input type="checkbox"></label></td>
                    <td>{row.fromTo}</td>
                    <td>{row.passengers.amount}</td>
                    <td style:color={getColorByValue(row.passengers.changedFromLY)}>
                        {row.passengers.changedFromLY}</td>
                    <td>{row.passengers.proportion}</td>
                    <td>{row.income.amount}</td>
                    <td style:color={getColorByValue(row.income.changedFromLY)}>
                        {row.income.changedFromLY}</td>
                    <td>{row.income.proportion}</td>
                    <td>{row.incomeRate.amount}</td>
                    <td style:color={getColorByValue(row.incomeRate.changedFromLY)}>
                        {row.incomeRate.changedFromLY}</td>
                    <td>{row.passengerTurnover.amount}</td>
                    <td style:color={getColorByValue(row.passengerTurnover.changedFromLY)}>
                        {row.passengerTurnover.changedFromLY}</td>
                    <td>{row.passengerTurnover.proportion}</td>
                    <td>{row.trainsCount}</td>
                </tr>
            {/if}
        {/each}
        </tbody>
    </table>
</ReportTemplate>

<style>
    labels {
        display: flex;
        justify-content: space-around;
        padding: 10px;
    }
</style>