using System.Json;

namespace VeritySDK
{
    /**
     * Factory for PresentProof protocol objects.
     * <p/>
     *
     * The PresentProof protocol allows one self-sovereign party ask another self-sovereign party for a private
     * and verifiable presentation from credentials they hold. This request can be restricted to certain selectable
     * restrictions.
     *
     * @see <a href="https://github.com/hyperledger/aries-rfcs/tree/4fae574c03f9f1013db30bf2c0c676b1122f7149/features/0037-present-proof" target="_blank" rel="noopener noreferrer">Aries 0037: Present Proof Protocol 1.0</a>
     */
    public class PresentProof
    {
        /**
         * Constructor for the 1.0 PresentProof object. This constructor creates an object that is ready to start
         * process of requesting a presentation of proof
         *
         * @param forRelationship the relationship identifier (DID) for the pairwise relationship that will be used
         * @param name A human readable name for the given request
         * @param proofAttrs An array of attribute based restrictions
         * @return 1.0 PresentProof object
         */
        public static PresentProofV1_0 v1_0(string forRelationship,
                                            string name,
                                            params Attribute[] proofAttrs)
        {
            return new PresentProofImplV1_0(forRelationship, name, proofAttrs);
        }

        /**
         * Constructor for the 1.0 PresentProof object. This constructor creates an object that is ready to start
         * process of requesting a presentation of proof
         *
         * @param forRelationship the relationship identifier (DID) for the pairwise relationship that will be used
         * @param name A human readable name for the given request
         * @param proofPredicate An array of predicate based restrictions
         * @return 1.0 PresentProof object
         */
        public static PresentProofV1_0 v1_0(string forRelationship,
                                            string name,
                                            params Predicate[] proofPredicate)
        {
            return new PresentProofImplV1_0(forRelationship, name, proofPredicate);
        }

        /**
         * Constructor for the 1.0 PresentProof object. This constructor creates an object that is ready to start
         * process of requesting a presentation of proof
         *
         * @param forRelationship the relationship identifier (DID) for the pairwise relationship that will be used
         * @param name A human readable name for the given request
         * @param proofAttrs An array of attribute based restrictions
         * @param proofPredicate An array of predicate based restrictions
         * @return 1.0 PresentProof object
         */
        public static PresentProofV1_0 v1_0(string forRelationship,
                                            string name,
                                            Attribute[] proofAttrs,
                                            Predicate[] proofPredicate)
        {
            return new PresentProofImplV1_0(forRelationship, name, proofAttrs, proofPredicate);
        }

        /**
         * Constructor for the 1.0 PresentProof object. This constructor re-creates an object from a known relationship and
         * threadId. This object can only check status of the protocol.
         *
         * @param forRelationship the relationship identifier (DID) for the pairwise relationship that will be used
         * @param threadId the thread id of the already started protocol
         * @return 1.0 PresentProof object
         */
        public static PresentProofV1_0 v1_0(string forRelationship,
                                            string threadId)
        {
            return new PresentProofImplV1_0(forRelationship, threadId);
        }
    }
}