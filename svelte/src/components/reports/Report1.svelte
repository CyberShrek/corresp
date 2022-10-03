<script>
    import ReportTemplate from "./ReportTemplate.svelte"
    import SquareLabel from "../common/SquareLabel.svelte"
    import {httpClient} from "../../web/httpClient"

    export let inputParams

    let rows = [{
        fromTo: undefined,
        passengersCount:   { count: undefined, changedFromLY: undefined, proportion: undefined },
        income:            { count: undefined, changedFromLY: undefined, proportion: undefined },
        incomeRate:        { count: undefined, changedFromLY: undefined },
        passengerTurnover: { count: undefined, changedFromLY: undefined, proportion: undefined },
        trainsCount: undefined
    }]

    $: promise = inputParams &&
        httpClient.createReport1ByParams(inputParams)

</script>

<ReportTemplate title="Список корреспонденций пассажиропотоков">
    <labels>
        <SquareLabel name="Пассажиры"          value="1111" img="img/passenger.png" background="#e2f0d9"/>
        <SquareLabel name="Тысяч рублей"       value="1111" img="img/wallet.png"    background="#dae3f3"/>
        <SquareLabel name="Рублей с пассажира" value="1234" img="img/ruble.png"     background="#fbe5d6"/>
        <SquareLabel name="Пасс-км"            value="12345" img="img/distance.png"  background="#fff2cc"/>
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
            <tr>
                <td><label><input type="checkbox"></label></td>
                <td>{row.fromTo}</td>
                <td>{row.passengersCount.count}</td>
                <td>{row.passengersCount.changedFromLY}</td>
                <td>{row.passengersCount.proportion}</td>
                <td>{row.income.count}</td>
                <td>{row.income.changedFromLY}</td>
                <td>{row.income.proportion}</td>
                <td>{row.incomeRate.count}</td>
                <td>{row.incomeRate.changedFromLY}</td>
                <td>{row.passengerTurnover.count}</td>
                <td>{row.passengerTurnover.changedFromLY}</td>
                <td>{row.passengerTurnover.proportion}</td>
                <td>{row.trainsCount}</td>
            </tr>
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
    table {
        border-collapse: collapse;
    }
    table th, table td { padding: 5px }
    table th {
        background: skyblue;
        color: white;
        font-weight: normal;
    }
    table td {
        border: var(--border);
    }

</style>