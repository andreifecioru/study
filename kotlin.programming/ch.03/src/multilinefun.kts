fun max(nums: IntArray): Int {
    var result = Int.MIN_VALUE

    for (num in nums) {
        result = if (num > result) num else result
    }

    return result
}

val nums = intArrayOf(1, 2, 5, 3)
println("Max value is: ${max(nums)}")
