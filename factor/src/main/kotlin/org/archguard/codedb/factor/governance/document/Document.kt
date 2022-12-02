package org.archguard.codedb.factor.governance.document

/**
 * A document is a piece of information that is created, received, and stored electronically.
 */
sealed class DocumentType {
    /**
     * Code of conduct is a document that describes the expected behavior of the members of the project.
     */
    class CodeOfConduct : DocumentType()

    /**
     * License is a legal document that grants the user the right to use the software.
     */
    class License : DocumentType()

    /**
     * Readme is a document that describes the project.
     */
    class Readme : DocumentType()

    /**
     * Release notes is a document that describes the changes in the project.
     */
    class ReleaseNotes : DocumentType()

    /**
     * Contribution guide is a document that describes how to contribute to the project.
     */
    class Contributing : DocumentType()
    class IssueTemplate : DocumentType()
    class PullRequestTemplate : DocumentType()

    /**
     * Security policy is a document that describes the security policy of the project.
     */
    class SecurityPolicy : DocumentType()
    class Other(str: String) : DocumentType()
}

