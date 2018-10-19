const memoize = (f) => {
    const cache = {}

    return (...args) => {
        const key = JSON.stringify(args)
        if (cache[key]) {
            console.log('Chache hit!')
            return cache[key]
        }
        console.log('Cache miss...')
        let result = f(args)
        cache[key] = result
        return result
    }
}

const memoize2 = (f) => {
    const cache = {}

    return (...args) => {
        const key = JSON.stringify(args)
        cache[key] = cache[key] || f(args)
        return cache[key]
    }
}

const square = memoize((x) => x * x)

console.log(square(4))
console.log(square(4))
console.log(square(4))