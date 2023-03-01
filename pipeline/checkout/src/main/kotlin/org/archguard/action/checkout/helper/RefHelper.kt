package org.archguard.action.checkout

import java.util.*

class RefHelper {
    val tagsRefSpec = "+refs/tags/*:refs/tags/*"

    fun getRefSpecForAllHistory(ref: String, commit: String): List<String> {
        val result = mutableListOf<String>()
        result.add("+refs/heads/*:refs/remotes/origin/*")
        result.add(tagsRefSpec)

        if (ref.isNotEmpty() && ref.uppercase(Locale.getDefault()).startsWith("REFS/PULL/")) {
            val branch = ref.substring("refs/pull/".length)
            result.add("+${commit}:refs/remotes/pull/${branch}")
        }

        return result
    }

    fun getCheckoutInfo(git: GitCommandManager, ref: String, commit: String): CheckoutInfo {
        val result = CheckoutInfo()

        // SHA only
        if (ref.isEmpty()) {
            result.ref = commit
        }
        // refs/heads/
        else if (ref.uppercase(Locale.getDefault()).startsWith("REFS/HEADS/")) {
            val branch = ref.substring("refs/heads/".length)
            result.ref = branch
            result.startPoint = "refs/remotes/origin/$branch"
        }
        // refs/pull/
        else if (ref.uppercase(Locale.getDefault()).startsWith("REFS/PULL/")) {
            val branch = ref.substring("refs/pull/".length)
            result.ref = "refs/remotes/pull/$branch"
        }
        // refs/tags/
        else if (ref.uppercase(Locale.getDefault()).startsWith("REFS/")) {
            result.ref = ref
        }
        // Unqualified ref, check for a matching branch or tag
        else {
            if (git.branchExists(true, "origin/$ref")) {
                result.ref = ref
                result.startPoint = "refs/remotes/origin/$ref"
            } else if (git.tagExists(ref)) {
                result.ref = "refs/tags/$ref"
            } else {
                throw Exception("A branch or tag with the name '$ref' could not be found")
            }
        }

        return result
    }
}

data class CheckoutInfo(
    var ref: String = "",
    var startPoint: String = "",
)