import java.util.ArrayList;
import com.google.gson.GsonBuilder;
public class BlockChain {
	
	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	public static int difficulty = 5;

	public static void main(String[] args) {	
		//add our blocks to the blockchain ArrayList:
		
		blockchain.add(new Block("Soy el prrimer bloque", "0"));
		System.out.println("Tratando de minar el bloque 1... ");
		blockchain.get(0).mineBlock(difficulty);
		
		blockchain.add(new Block("Soy el segundo bloque",blockchain.get(blockchain.size()-1).hash));
		System.out.println("Tratando de minar el bloque 2... ");
		blockchain.get(1).mineBlock(difficulty);
		
		blockchain.add(new Block("Soy el tercer bloque",blockchain.get(blockchain.size()-1).hash));
		System.out.println("Tratando de minar el bloque 3... ");
		blockchain.get(2).mineBlock(difficulty);	
		
		System.out.println("\nBlockchain es valido: " + isChainValid());
		
		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
		System.out.println("\nEl block chain: ");
		System.out.println(blockchainJson);
	}
	
	public static Boolean isChainValid() {
		Block currentBlock; 
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		
		//loop through blockchain to check hashes:
		for(int i=1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i-1);
			//compare registered hash and calculated hash:
			if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
				System.out.println("Hashes actuales no son iguales");			
				return false;
			}
			//compare previous hash and registered previous hash
			if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
				System.out.println("Hashes previos no son iguales");
				return false;
			}
			//check if hash is solved
			if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
				System.out.println("Este block no ha sido minado");
				return false;
			}
		}
		return true;
	}
}
