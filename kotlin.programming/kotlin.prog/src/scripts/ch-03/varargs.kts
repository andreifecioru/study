fun max(vararg nums: Int): Int {
    var result = Int.MIN_VALUE

    // the actual type of the input param is IntArray
    println("Input param type: ${nums::class}")

    for (num in nums) {
        result = if (num > result) num else result
    }

    return result
}

println("Max value is: ${max(1, 2, 5, 3)}")

// use the spread operator if you already have a collection of values
val nums = intArrayOf(1, 2, 5, 3)
println("Max value is: ${max(*nums)}")

// lists need to be converted to arrays first
val nums1 = listOf(1, 2, 5, 3)
println("Max value is: ${max(*nums1.toIntArray())}")
