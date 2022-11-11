export function declineNumeral(value, options){
    const num = Math.floor(Math.abs(value)) % 10
    return (num === 1) ? options[0]
        : (num > 0 && num < 5) ? options[1]
            : options[2]
}

export const filtrate=(value, filterValue) =>
    filterValue ? value.toUpperCase().includes(filterValue.toUpperCase()) : true

export const scrollIntoEvent=(event) => scrollIntoElement(event.target)

export const scrollIntoElement=(element) => element.scrollIntoView({ behavior: "smooth", block: "start"})


