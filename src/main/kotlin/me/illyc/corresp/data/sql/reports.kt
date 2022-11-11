package me.illyc.corresp.data.sql

import me.illyc.corresp.model.ReportInputParams

fun getReport1ByParams(params: ReportInputParams) = ReportTemplate(params,
    isMainReport = true,
    source       = "correspvag",
    beforeBaseSelect = """correspon.stanotp  AS from_code,
                          correspon.stannazn AS to_code,
                          from_.pnazv||' -> '||to_.pnazv AS from_to_names,
                          count(DISTINCT trainpass.pzd) AS trains_count""",

    afterBaseFrom = """ JOIN nsi.stanv AS from_ ON (from_.stan = correspon.stanotp
                            AND '${params.date1}' between from_.datand and from_.datakd
                            AND '${params.date1}' between from_.datani and from_.dataki)
                        JOIN nsi.stanv AS to_ ON (to_.stan = correspon.stannazn
                            AND '${params.date1}' between to_.datand and to_.datakd
                            AND '${params.date1}' between to_.datani and to_.dataki)""",

    afterBaseWhere = """ AND (correspvag.viddok IS NULL
                           OR correspvag.viddok != '6'
                          AND correspvag.viddok != ' '
                          AND correspvag.viddok != 'Z')""",

    groupBaseBy             = "from_code, to_code, from_to_names",
    beforeResultSelect      = "from_code, to_code, from_to_names, base.trains_count",
    beforeResultTotalSelect = "null,      null,    'TOTAL',       null",
    orderResultBy           = "from_to_names",

    beforeComparedSelect = "from_code, to_code, curr_year.from_to_names, curr_year.trains_count",
    comparatorKey        = "from_code, to_code"
)

fun getReport2ByParams(params: ReportInputParams) = ReportTemplate(params,
    beforeBaseSelect = """trainpass.pzd            AS train_num, 
                         (trainpass.pr_firm = '1') AS is_branded, 
                          pzdc.names               AS category_name""",
    afterBaseFrom    = """JOIN nsi.katpzdco32 AS pzdc 
                          ON regexp_replace(pzd, '[A-Za-zА-Яа-я]', '', 'g')::int BETWEEN pzdosnn AND pzdosnk""",

    groupBaseBy             = "train_num, is_branded, category_name",
    beforeResultTotalSelect = "'TOTAL',   null,       null",
    orderResultBy           = "train_num",

    beforeComparedSelect = "train_num, curr_year.is_branded, curr_year.category_name",
    comparatorKey        = "train_num"
)

fun getDailyReport3ByParams(params: ReportInputParams) = ReportTemplate(params,
    beforeWithBase = """date_series AS (
                SELECT date::date, to_char(date,'DY') as day
                FROM generate_series('${params.date1}', '${params.date2}', interval '1 day') AS date
            )""",
    beforeBaseSelect = "correspon.dateotp AS date",
    groupBaseBy      = "date",

    beforeResultSelect = "date, day",
    afterResultWhere   = "RIGHT JOIN date_series USING (date)",

    beforeResultTotalSelect = "null, 'TOTAL'",

    orderResultBy = "date"
)

fun getMonthlyReport3ByParams(params: ReportInputParams) = ReportTemplate(params,
    beforeWithBase = """date_series AS (
                SELECT date::date, to_char(date,'YY') AS year, to_char(date,'MON') as month
                FROM generate_series(
                          date_trunc('month', '${params.date1}'::date - '1 year'::interval), 
                          '${params.date2}', interval '1 month') AS date
            )""",
    beforeBaseSelect = """to_char(dateotp,'YY') as year, 
                                  to_char(dateotp,'MON') as month""",
    date1 = "date_trunc('month', '${params.date1}'::date - '1 year'::interval)",
    groupBaseBy      = "year, month",

    afterResultWhere   = "RIGHT JOIN date_series USING (year, month)",

    beforeResultSelect = "date, year, month",

    beforeResultTotalSelect = "null, null, 'TOTAL'",

    orderResultBy = "date"
)

fun getReport4ByParams(params: ReportInputParams) = ReportTemplate(params,
    source = "correspvag",
    beforeBaseSelect = """correspvag.katvag AS car_category, 
                          correspvag.klov   AS service_class""",
    groupBaseBy      = "car_category, service_class",

    beforeResultTotalSelect = "'TOTAL',      null",
    orderResultBy           = "car_category, service_class"
)

fun getReport5ByParams(params: ReportInputParams) = ReportTemplate(params,
    source = "correspvag",
    beforeBaseSelect = "correspvag.viddok AS doc_type",
    groupBaseBy      = "doc_type",

    beforeResultTotalSelect = "'TOTAL'"
)

fun getReport6ByParams(params: ReportInputParams) = ReportTemplate(params,
    source = "passprofile",
    beforeBaseSelect = """lgot.snazv AS benefit_name,  
                          tplan.itxt AS tariff_plan""",

    afterBaseFrom = """JOIN nsi.lgot	          AS lgot	     ON (lgot.lgot  = correspvag.numlgot)
                       JOIN nsi.tplan             AS tplan       ON (tplan.lgot = correspvag.numlgot)
                       JOIN rmest.day_passprofile AS passprofile ON (tplan.kodn = passprofile.tarpl)
                       """,

    afterBaseWhere = """AND correspvag.viddok = 'L'""",

    groupBaseBy             = "benefit_name,  tariff_plan",
    beforeResultTotalSelect = "'TOTAL',       null",
    orderResultBy           = "benefit_name"
)

fun getReport7ByParams(params: ReportInputParams) = ReportTemplate(params,
    source = "passprofile",
    beforeBaseSelect        = """passprofile.prmg AS gender_sign, 
                                 passprofile.ktar AS tariff_code""",
    afterBaseFrom           = "JOIN rmest.day_passprofile AS passprofile USING (id_corresp)",
    groupBaseBy             = "gender_sign, tariff_code",

    beforeResultTotalSelect = "'TOTAL',     null",
    orderResultBy           = "gender_sign, tariff_code"
)