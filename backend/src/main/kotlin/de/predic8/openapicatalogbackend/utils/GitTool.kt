package de.predic8.openapicatalogbackend.utils

import de.predic8.openapicatalogbackend.model.dto.specification.UpdateDto
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.api.Git.cloneRepository
import org.eclipse.jgit.api.Git.open
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.eclipse.jgit.treewalk.TreeWalk
import org.eclipse.jgit.treewalk.TreeWalk.forPath
import org.springframework.stereotype.Component
import java.io.File
import java.time.LocalDateTime

@Component
class GitTool(val tools: Tools) {
    fun getDocumentsBy(lastFetch: LocalDateTime?, update: UpdateDto): List<String> = getRepositoryBy(update.url.url).getContentHistoryBy(lastFetch = lastFetch, path = update.path!!)

    private fun cloneRepositoryBy(path: File, sourceUrl: String): Git = cloneRepository().setURI(sourceUrl).setDirectory(path).call()

    private fun TreeWalk.getContent(): String = objectReader.open(getObjectId(0)).bytes.decodeToString()

    private fun Git.getContentHistoryBy(lastFetch: LocalDateTime?, path: String): List<String> =
        getContentLogBy(path).filter { lastFetch?.let { lf -> it.committerIdent?.run { whenAsInstant.atZone(zoneId).toLocalDateTime() > lf } } ?: true }.flatMap { getContentHistoryBy(commit = it, path = path) }

    private fun Git.getContentHistoryBy(commit: RevCommit, path: String): List<String> =
        buildList { forPath(repository, path, commit.tree).use { do add(it.getContent()) while (it.next()) } }

    private fun Git.getContentLogBy(path: String): MutableIterable<RevCommit> = log().addPath(path).call()

    private fun getRepositoryBy(path: File): Git? = FileRepositoryBuilder().findGitDir(path).gitDir?.let(::open)

    private fun getRepositoryBy(sourceUrl: String): Git = tools.createTempFolder().let { getRepositoryBy(it) ?: cloneRepositoryBy(path = it, sourceUrl = sourceUrl) }.apply { pull().call() }
}
