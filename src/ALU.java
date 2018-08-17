
/**
 * ģ��ALU���������͸���������������
 * @author 161250121_�ν���
 *
 */

public class ALU {

	/**
	 * ����ʮ���������Ķ����Ʋ����ʾ��<br/>
	 * ����integerRepresentation("9", 8)
	 * @param number ʮ������������Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param length �����Ʋ����ʾ�ĳ���
	 * @return number�Ķ����Ʋ����ʾ������Ϊlength
	 */
	public String integerRepresentation (String number, int length) {
		// TODO YOUR CODE HERE.
		
		int NUMBER=Integer.parseInt(number);
		String BinaryString=Integer.toBinaryString(NUMBER);
		StringBuffer sb;
		if(BinaryString.length()>length){
			BinaryString=BinaryString.substring(BinaryString.length()-length ,BinaryString.length());
			return BinaryString;
		}
		else{
			sb=new StringBuffer(BinaryString);
			if(NUMBER>=0){
				for(int count=0;count<length-BinaryString.length();count++)
					sb.insert(0, "0");
			}
			else{
				for(int count=0;count<length-BinaryString.length();count++){
					sb.insert(0, "1");
				}
			}
		}
		BinaryString=sb.toString();
		return BinaryString;
	}
	
	/**
	 * ����ʮ���Ƹ������Ķ����Ʊ�ʾ��
	 * ��Ҫ���� 0������񻯡����������+Inf���͡�-Inf������ NaN�����أ������� IEEE 754��
	 * �������Ϊ��0���롣<br/>
	 * ����floatRepresentation("11.375", 8, 11)
	 * @param number ʮ���Ƹ�����������С���㡣��Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return number�Ķ����Ʊ�ʾ������Ϊ 1+eLength+sLength���������ң�����Ϊ���š�ָ���������ʾ����β������λ���أ�
	 */
	public String floatRepresentation (String number, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		
		String temp=number;
		int symbol=0;        // ����λ
		String ePart="";     // ָ��
		String sPart="";     // β��
		String result=null;
		if(number.equals("+Inf")){
			for(int n=0;n<eLength;n++){
				ePart=ePart+"1";
			}
			for(int n=0;n<sLength;n++){
				sPart=sPart+"0";
			}
			symbol=0;
			return symbol+ePart+sPart;
		}
		else if(number.equals("-Inf")){
			for(int n=0;n<eLength;n++){
				ePart=ePart+"1";
			}
			for(int n=0;n<sLength;n++){
				sPart=sPart+"0";
			}
			symbol=1;
			return symbol+ePart+sPart;
		}
		
		else{
		String INTEGER_PART=number.split("\\.")[0];
		if(INTEGER_PART.startsWith("-")){
			INTEGER_PART=INTEGER_PART.substring(1,INTEGER_PART.length());
			symbol=1;
			temp=number.substring(1,number.length());
		}
		
		String FRACTIONAL_PART="0."+number.split("\\.")[1];
		String tempFractional="";
		double fractional_D=Double.parseDouble(FRACTIONAL_PART);
		
		int x=0;
		while(fractional_D!=0&&x!=sLength*2){
			fractional_D=fractional_D*2;
			if(fractional_D>=1){
				tempFractional+="1";
				fractional_D--;
			}else{
				tempFractional+="0";
			}
			x++;
		}
		
		int e=(1<<eLength-1)-1;
		double theNumber=Double.parseDouble(temp);
		
		if(theNumber>=1){
			String INTEGER_B=Integer.toBinaryString(Integer.parseInt(INTEGER_PART));
			ePart=Integer.toBinaryString(e+INTEGER_B.length()-1);
			sPart=INTEGER_B.substring(1, INTEGER_B.length())+tempFractional;
			
		}else if(theNumber==0.0){
			for(int n=0;n<eLength;n++)
				ePart=ePart+"0";
			for(int n=0;n<sLength;n++)
				sPart=sPart+"0";
		}
		else{
			int n=-1;
			while(!tempFractional.startsWith("1")){
				tempFractional=tempFractional.substring(1, tempFractional.length());
				n--;
			}
			if(n>=2-(1<<(eLength-1))){
				ePart=Integer.toBinaryString(e+n);
				sPart=tempFractional.substring(1,tempFractional.length());
				
			}else{
				
				for(int m=0;m<eLength;m++){
					ePart+="0";
				}
				for(int m=0;m<1-(1<<(eLength-1))-n;m++){
					sPart="0"+sPart;
				}
				sPart+=tempFractional;
			}
		}
		if(ePart.length()>eLength){
		    ePart=ePart.substring(ePart.length()-eLength,ePart.length());
		}else{
			while(ePart.length()<eLength){
				ePart="0"+ePart;
			}
		}
		if(sPart.length()>sLength){
			sPart=sPart.substring(0, sLength);
		}else{
			while(sPart.length()<sLength){
				sPart=sPart+"0";
			}
		}	
		boolean toNext=true;
		for(int n=0;n<eLength;n++){
			if(!ePart.substring(n,n+1).equals("1")){
				result=symbol+ePart+sPart;
				toNext=false;
				break;
			}
		}
	    if(!toNext){
	    	return result;
	    }else{
			for(int n=0;n<sLength;n++){
				if(!sPart.substring(n,n+1).equals("0")){
					result="NaN";
					return result;
				}
			}
			if(symbol==1){
				return "-Inf";
			}else{
				return "+Inf";
			}
			}
		}
	}
	
	/**
	 * ����ʮ���Ƹ�������IEEE 754��ʾ��Ҫ�����{@link #floatRepresentation(String, int, int) floatRepresentation}ʵ�֡�<br/>
	 * ����ieee754("11.375", 32)
	 * @param number ʮ���Ƹ�����������С���㡣��Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param length �����Ʊ�ʾ�ĳ��ȣ�Ϊ32��64
	 * @return number��IEEE 754��ʾ������Ϊlength���������ң�����Ϊ���š�ָ���������ʾ����β������λ���أ�
	 */
	public String ieee754 (String number, int length) {
		// TODO YOUR CODE HERE.
		String result="";
		if(number.equals("0")){
			for(int n=0;n<length;n++)
				result=result+"0";
			return result;
		}
		else if(length==32){
			return new ALU().floatRepresentation(number, 8, 23);
		}else{
			return new ALU().floatRepresentation(number, 11, 52);
		}
	}
	
	/**
	 * ��������Ʋ����ʾ����������ֵ��<br/>
	 * ����integerTrueValue("00001001")
	 * @param operand �����Ʋ����ʾ�Ĳ�����
	 * @return operand����ֵ����Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 */
	public String integerTrueValue (String operand) {
		// TODO YOUR CODE HERE.
		boolean IsPositive=true;
		String temp="";
		if(operand.substring(0, 1).equals("1")){
			IsPositive=false;
		}
		if(!IsPositive){
			for(int n=1;n<operand.length();n++){
				if(operand.substring(n,n+1).equals("1")){
					temp+="0";
				}
				else{
					temp+="1";
				}
			}
		}else{
			temp=operand;
		}
		int result=Integer.parseInt(temp,2);
		if(!IsPositive){
			result++;
			result=-result;
		}
		return result+"";
	}
	
	/**
	 * ���������ԭ���ʾ�ĸ���������ֵ��<br/>
	 * ����floatTrueValue("01000001001101100000", 8, 11)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return operand����ֵ����Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ����������ֱ��ʾΪ��+Inf���͡�-Inf���� NaN��ʾΪ��NaN��
	 */
	public String floatTrueValue (String operand, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		String symbol=operand.substring(0,1);
		String ePart=operand.substring(1,eLength+1);
		String sPart=operand.substring(eLength+1,sLength+eLength+1);
		
		String temp1="";
		for(int n=0;n<eLength;n++){
			temp1+="1";
		}
		String temp2="";
		for(int n=0;n<sLength;n++){
			temp2+="0";
		}
		String temp3="";
		for(int n=0;n<eLength;n++){
			temp3+="0";
		}
		
		if(ePart.equals(temp1)){
			if(sPart.equals(temp2)&&symbol.equals("1"))
				return "-Inf";
			else if(sPart.equals(temp2)&&symbol.equals("0"))
				return "+Inf";
			else
				return "NaN";
		}else{
			double result=0;
			int e=(1<<eLength-1)-1;
			if(ePart.equals(temp3)){
				e=-e;
			}else{
				e=Integer.parseInt(ePart,2)-e;
				sPart="1"+sPart;
			}
		    if(e>=0){
				String integerPart=sPart.substring(0,e+1);
				sPart=sPart.substring(e+1,sPart.length());
				result+=Integer.parseInt(integerPart,2);
			}else{
				for(int n=0;n<-e-1;n++){
					sPart="0"+sPart;
				}
			}
			for(int n=0;n<sPart.length();n++){
				if(sPart.substring(n,n+1).equals("1")){
					double temp=1;
					for(int m=0;m<n+1;m++)
						temp=temp/2;
					result+=temp;
				}
			}
			String strResult=result+"";
			
			while(strResult.substring(strResult.length()-1, strResult.length()).equals("0")&&!strResult.substring(strResult.length()-2, strResult.length()-1).equals(".")){
				strResult=strResult.substring(0,strResult.length()-1);
			}
			if(symbol.equals("1")){
				strResult="-"+strResult;
			}
			return strResult;
		}
	}
	
	/**
	 * ��λȡ��������<br/>
	 * ����negation("00001001")
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @return operand��λȡ���Ľ��
	 */
	public String negation (String operand) {
		// TODO YOUR CODE HERE.
		String result="";
		for(int n=0;n<operand.length();n++){
			if(operand.substring(n, n+1).equals("0"))
				result+="1";
			else
				result+="0";
		}		
		return result;
	}
	
	/**
	 * ���Ʋ�����<br/>
	 * ����leftShift("00001001", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand����nλ�Ľ��
	 */
	public String leftShift (String operand, int n) {
		// TODO YOUR CODE HERE.
		String result=operand;
		for(int m=0;m<n;m++){
			result=result+"0";
		}
		result=result.substring(result.length()-operand.length(), result.length());
		return result;
	}
	
	/**
	 * �߼����Ʋ�����<br/>
	 * ����logRightShift("11110110", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand�߼�����nλ�Ľ��
	 */
	public String logRightShift (String operand, int n) {
		// TODO YOUR CODE HERE.
		String result=operand;
		for(int m=0;m<n;m++)
			result="0"+result;
		result=result.substring(0, operand.length());
		return result;
	}
	
	/**
	 * �������Ʋ�����<br/>
	 * ����logRightShift("11110110", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand��������nλ�Ľ��
	 */
	public String ariRightShift (String operand, int n) {
		// TODO YOUR CODE HERE.
		String result=operand;
		if(operand.startsWith("1")){
			for(int m=0;m<n;m++)
				result="1"+result;
		}else{
			for(int m=0;m<n;m++)
				result="0"+result;
		}
		result=result.substring(0, operand.length());
		return result;
	}
	
	/**
	 * ȫ����������λ�Լ���λ���мӷ����㡣<br/>
	 * ����fullAdder('1', '1', '0')
	 * @param x ��������ĳһλ��ȡ0��1
	 * @param y ������ĳһλ��ȡ0��1
	 * @param c ��λ�Ե�ǰλ�Ľ�λ��ȡ0��1
	 * @return ��ӵĽ�����ó���Ϊ2���ַ�����ʾ����1λ��ʾ��λ����2λ��ʾ��
	 */
	public String fullAdder (char x, char y, char c) {
		// TODO YOUR CODE HERE.
		int temp=0;
		if(x=='1') temp++;
		if(y=='1') temp++;
		if(c=='1') temp++;
		String result=Integer.toBinaryString(temp);
		if(result.length()==1){
			result="0"+result;
		}
		
		return result;
	}
	
	/**
	 * 4λ���н�λ�ӷ�����Ҫ�����{@link #fullAdder(char, char, char) fullAdder}��ʵ��<br/>
	 * ����claAdder("1001", "0001", '1')
	 * @param operand1 4λ�����Ʊ�ʾ�ı�����
	 * @param operand2 4λ�����Ʊ�ʾ�ļ���
	 * @param c ��λ�Ե�ǰλ�Ľ�λ��ȡ0��1
	 * @return ����Ϊ5���ַ�����ʾ�ļ����������е�1λ�����λ��λ����4λ����ӽ�������н�λ��������ѭ�����
	 */
	public String claAdder (String operand1, String operand2, char c) {
		// TODO YOUR CODE HERE.
		String result="";
		char[]operand1C=operand1.toCharArray();
		char[]operand2C=operand2.toCharArray();
		char thisC=c;
		for(int n=3;n>=0;n--){
			String temp=fullAdder(operand1C[n],operand2C[n],thisC);
			result=temp.substring(1, 2)+result;
			thisC=temp.charAt(0);
			
		}
		result=thisC+result;
		return result;
	}
	
	/**
	 * ��һ����ʵ�ֲ�������1�����㡣
	 * ��Ҫ�������š����š�����ŵ�ģ�⣬
	 * ������ֱ�ӵ���{@link #fullAdder(char, char, char) fullAdder}��
	 * {@link #claAdder(String, String, char) claAdder}��
	 * {@link #adder(String, String, char, int) adder}��
	 * {@link #integerAddition(String, String, int) integerAddition}������<br/>
	 * ����oneAdder("00001001")
	 * @param operand �����Ʋ����ʾ�Ĳ�����
	 * @return operand��1�Ľ��������Ϊoperand�ĳ��ȼ�1�����е�1λָʾ�Ƿ���������Ϊ1������Ϊ0��������λΪ��ӽ��
	 */
	public String oneAdder (String operand) {
		// TODO YOUR CODE HERE.
		int Y=1;
		int C=0;
		String result="";
		int temp1=0;
		int temp2=0;
		for(int n=operand.length();n>=1;n--){
			int X=Integer.parseInt(operand.substring(n-1, n));
			int F=X^Y^C;
			C=(X&C)|(Y&C)|(X&Y);
			Y=0;
			result=F+result;
			if(n==2){
				temp1=C;
			}
			else if(n==1){
				temp2=C;
			}
		}
		int temp3=temp1^temp2;
		result=temp3+result;
		return result;
	}
	
	/**
	 * �ӷ�����Ҫ�����{@link #claAdder(String, String, char)}����ʵ�֡�<br/>
	 * ����adder("0100", "0011", ��0��, 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param c ���λ��λ
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����ӽ��
	 */
	public String adder (String operand1, String operand2, char c, int length) {
		// TODO YOUR CODE HERE.
		String tempOperand1=operand1;
		String tempOperand2=operand2;
		int symbol1=0;
		int symbol2=0;
		if(operand1.charAt(0)=='1'){
			symbol1++;
		}
		if(operand2.charAt(0)=='1'){
			symbol2++;
		}
		
		if(tempOperand1.length()<length){
			int temp=length-tempOperand1.length();
			if(tempOperand1.charAt(0)=='1'){
				for(int n=0;n<temp;n++){
					tempOperand1="1"+tempOperand1;
					
				}
			}else{
				for(int n=0;n<temp;n++){
					tempOperand1="0"+tempOperand1;
					
				}
			}
		}
		if(tempOperand2.length()<length){
			int temp=length-tempOperand2.length();
			if(tempOperand2.charAt(0)=='1'){
				for(int n=0;n<temp;n++){
					tempOperand2="1"+tempOperand2;
					
				}
			}else{
				for(int n=0;n<temp;n++){
					tempOperand2="0"+tempOperand2;
					
				}
			}
		}
        String result="";
        String temp;
        char tempC=c;
        for(int n=length;n>=4;n-=4){
        	String loopTemp=claAdder(tempOperand1.substring(n-4, n),tempOperand2.substring(n-4, n),tempC);
        	temp=loopTemp.substring(1,5);
            tempC=loopTemp.charAt(0);
            result=temp+result;
        }
        if(symbol1!=symbol2){
        	result="0"+result;
        }else{
        	if(symbol1==1&&result.charAt(0)=='0'){
        		result="1"+result;
        	}
        	else if(symbol1==0&&result.charAt(0)=='1'){
        		result="1"+result;
        	}else{
        		result="0"+result;
        	}
        }
        return result;
	}
	
	/**
	 * �����ӷ���Ҫ�����{@link #adder(String, String, char, int) adder}����ʵ�֡�<br/>
	 * ����integerAddition("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����ӽ��
	 */
	public String integerAddition (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		String tempOperand1=operand1;
		String tempOperand2=operand2;
		int operand1Length=tempOperand1.length();
		int operand2Length=tempOperand2.length();
		if(operand1Length<length){
			if(tempOperand1.charAt(0)=='0'){
				for(int n=0;n<length-operand1Length;n++){
					tempOperand1="0"+tempOperand1;
				}
			}else{
				for(int n=0;n<length-operand1Length;n++){
					tempOperand1="1"+tempOperand1;
				}
			}
		}
		if(operand2Length<length){
			if(tempOperand2.charAt(0)=='0'){
				for(int n=0;n<length-operand1Length;n++){
					tempOperand2="0"+tempOperand2;
				}
			}else{
				for(int n=0;n<length-operand1Length;n++){
					tempOperand2="1"+tempOperand2;
				}
			}
		}
		String result=adder(operand1,operand2,'0',length);
		return result;
	}
	
	/**
	 * �����������ɵ���{@link #adder(String, String, char, int) adder}����ʵ�֡�<br/>
	 * ����integerSubtraction("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ��������
	 */
	public String integerSubtraction (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		String negativeOne="";
		for(int n=0;n<operand2.length();n++){
			negativeOne=negativeOne+"1";
		}
		int tempCountInt=0;
		for(int n=length;n>=0;n-=4){
			if(operand2.length()>=n){
				tempCountInt=n+4;
				break;
			}
		}
		String tempOperand2=adder(operand2,negativeOne,'0',tempCountInt).substring(1,tempCountInt+1);
		
		String tempStr="";
		for(int n=0;n<tempOperand2.length();n++){
			if(tempOperand2.charAt(n)=='1')
				tempStr=tempStr+"0";
			else
				tempStr=tempStr+"1";
		}
		if(tempStr.length()<length){
			int temp=length-tempStr.length();
			if(tempStr.charAt(0)=='1')
				for(int n=0;n<temp;n++)
					tempStr="1"+tempStr;
			else
				for(int n=0;n<temp;n++)
					tempStr="0"+tempStr;
		}else{
			tempStr=tempStr.substring(tempStr.length()-length, tempStr.length());
		}
		
		
		
		String result=adder(operand1,tempStr,'0',length);
		
		return result;
	}
	
	/**
	 * �����˷���ʹ��Booth�㷨ʵ�֣��ɵ���{@link #adder(String, String, char, int) adder}�ȷ�����<br/>
	 * ����integerMultiplication("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ĳ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ����˽�������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����˽��
	 */
	public String integerMultiplication (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		String tempOperand1=operand1;
		String tempOperand2=operand2;
		String symbol;
		if((tempOperand1.charAt(0)=='1'&&tempOperand2.charAt(0)=='0')||(tempOperand1.charAt(0)=='0'&&tempOperand2.charAt(0)=='1')){
			symbol="1";
		}else{
			symbol="0";
		}
		int tempCount=tempOperand2.length();
		if(tempOperand1.length()>tempOperand2.length()){
			if(tempOperand2.charAt(0)=='1'){
				for(int n=0;n<tempOperand1.length()-tempCount;n++){
					tempOperand2="1"+tempOperand2;
				}
			}else{
				for(int n=0;n<tempOperand1.length()-tempCount;n++){
					tempOperand2="0"+tempOperand2;
				}
			}
		}
		
		String X=tempOperand1;
		String result="";
		String Y=tempOperand2;
		
		String YSub1="0";
		String Y1=Y.substring(Y.length()-1, Y.length());
		
		String P="";
		
		for(int n=0;n<tempOperand2.length();n++){
			P+="0";
		}
		for(int n=0;n<tempOperand2.length();n++){
			String temp=Y1+YSub1;
			switch(temp){
			case "01":P=integerAddition(P,X,tempOperand1.length()).substring(1,tempOperand1.length()+1);break;
			case "10":P=integerSubtraction(P, X, tempOperand1.length()).substring(1,tempOperand1.length()+1);break;
			default: break;
			}
			if(P.charAt(0)=='0'){
				P="0"+P;
			}else{
				P="1"+P;
			}
			Y=P.charAt(P.length()-1)+Y;
			YSub1=Y.charAt(Y.length()-1)+"";
			Y1=Y.charAt(Y.length()-2)+"";
			
			
			Y=Y.substring(0,Y.length()-1);
			P=P.substring(0, P.length()-1);
			
		}
		result=P+Y;
		
		int count=result.length();
		if(count<length){
			if(result.charAt(0)=='1'){
				for(int n=0;n<length-count;n++){
					result="1"+result;
				}
			}else{
				for(int n=0;n<length-count;n++){
					result="0"+result;
				}
			}
		}
		else{
			result=result.substring(result.length()-length, result.length());
		}
		if(symbol.equals(result.charAt(0)+""))
			result="0"+result;
		else
			result="1"+result;
		return result;
	}
	
	/**
	 * �����Ĳ��ָ������������ɵ���{@link #adder(String, String, char, int) adder}�ȷ���ʵ�֡�<br/>
	 * ����integerDivision("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ĳ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊ2*length+1���ַ�����ʾ�������������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0�������lengthλΪ�̣����lengthλΪ����
	 */
	public String integerDivision (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		boolean divisionZero=true;
		for(char c:operand2.toCharArray()){
			if(c=='1'){
				divisionZero=false;
				break;
			}
			else
				continue;
		}
		if(divisionZero)
			return "NaN";
		else{
			String X=operand1;
			String Y=operand2;
			
			int count=X.length();
			int countY=Y.length();
			if(X.charAt(0)=='1'){
				for(int n=0;n<length*2-count;n++){
					X="1"+X;
			}
			}else{
				for(int n=0;n<length*2-count;n++){
					X="0"+X;
				}
			}
			
			String R=X.substring(0, length);
			String Q=X.substring(length,length*2);
			
			if(Y.charAt(0)=='1'){
				for(int n=0;n<length-countY;n++){
					Y="1"+Y;
			}
			}else{
				for(int n=0;n<length-countY;n++){
					Y="0"+Y;
				}
			}
			String first="0";
			String Qn;
		    
			if(X.charAt(0)==Y.charAt(0)){
				R=integerSubtraction(R,Y,length).substring(1,length+1);
			}else{
				R=integerAddition(R,Y,length).substring(1,length+1);
			}
			
			if(R.charAt(0)==Y.charAt(0)){
				Qn="1";
			}else{
				Qn="0";
			}
			if(((X.charAt(0)==Y.charAt(0))&&(R.charAt(0)==Y.charAt(0)))||((X.charAt(0)!=Y.charAt(0))&&(R.charAt(0)!=Y.charAt(0)))){
				first="1";
			}
			
			for(int n=0;n<length;n++){

				
				
				if(R.charAt(0)==Y.charAt(0)){
					R=R.substring(1, length)+Q.charAt(0);
					Q=Q.substring(1, length)+Qn;
					
					
					R=integerSubtraction(R,Y,length).substring(1,length+1);
				}else{
					R=R.substring(1, length)+Q.charAt(0);
					Q=Q.substring(1, length)+Qn;
					
					R=integerAddition(R,Y,length).substring(1,length+1);
				}
			
				
				if(R.charAt(0)==Y.charAt(0)){
					Qn="1";
				}else{
					Qn="0";
				}
				
				
				if(n==length-1){
					
					if(R.charAt(0)==Y.charAt(0)){
						Q=Q.substring(1,length)+"1";
					}else{
						Q=Q.substring(1,length)+"0";
					}
					
					
					if(X.charAt(0)!=Y.charAt(0)){
						Q=integerAddition(Q,"01",length).substring(1,length+1);
					}

					
					
					if(R.charAt(0)!=X.charAt(0)){
						if(X.charAt(0)==Y.charAt(0)){
							R=integerAddition(R,Y,length).substring(1,length+1);
						}else{
							R=integerSubtraction(R,Y,length).substring(1,length+1);
						}
					}
				}
				
				
			}
			
			if(operand1.charAt(0)=='1'&&operand2.charAt(0)=='0'){
				String negativeOneForResult="";
				for(int n=0;n<length;n++){
					negativeOneForResult+="1";
				}
				Q=integerAddition(Q,negativeOneForResult,length).substring(1,length+1);
				R=integerAddition(R,operand2,length).substring(1,length+1);
			}
			
			return first+Q+R;
		}
	}
	
	/**
	 * �����������ӷ������Ե���{@link #adder(String, String, char, int) adder}�ȷ�����
	 * ������ֱ�ӽ�������ת��Ϊ�����ʹ��{@link #integerAddition(String, String, int) integerAddition}��
	 * {@link #integerSubtraction(String, String, int) integerSubtraction}��ʵ�֡�<br/>
	 * ����signedAddition("1100", "1011", 8)
	 * @param operand1 ������ԭ���ʾ�ı����������е�1λΪ����λ
	 * @param operand2 ������ԭ���ʾ�ļ��������е�1λΪ����λ
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ����������ţ�����ĳ���������ĳ���С��lengthʱ����Ҫ���䳤����չ��length
	 * @return ����Ϊlength+2���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������2λΪ����λ����lengthλ����ӽ��
	 */
	public String signedAddition (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		
		char symbol1=operand1.charAt(0);
		char symbol2=operand2.charAt(0);
		String tempOperand1=operand1.substring(1,operand1.length());
		String tempOperand2=operand2.substring(1,operand2.length());
		
		if(tempOperand1.length()<length){
			tempOperand1="0"+tempOperand1;
		}
		if(tempOperand2.length()<length){
			tempOperand2="0"+tempOperand2;
		}
		String tempResult;
		if(symbol1==symbol2){
			int location1=length;
			int location2=length;
			int location3=length;
			for(int n=length-tempOperand1.length();n<length;n++){
				if(tempOperand1.charAt(n-length+tempOperand1.length())=='1'){
					location1=n;
					break;
				}
			}
			for(int n=length-tempOperand2.length();n<length;n++){
				if(tempOperand2.charAt(n-length+tempOperand2.length())=='1'){
					location2=n;
					break;
				}
			}
			
			tempResult=adder(tempOperand1,tempOperand2,'0',length).substring(1,length+1);
			for(int n=0;n<length;n++){
				if(tempResult.charAt(n)=='1'){
					location3=n;
					break;
				}
			}
			char isOut;
			
			if(location3>Math.max(location1, location2))
				isOut='1';
			else
				isOut='0';
			return (isOut+"")+(symbol1+"")+tempResult;
		}else{
			String tempString="";
			for(int n=0;n<length;n++){
				tempString+="1";
			}
			
			
            tempOperand2=adder(tempOperand2,tempString,'0',length).substring(1,length+1);
            String temptempOperand2="";
            for(int n=0;n<length;n++){
            	if(tempOperand2.charAt(n)=='1')
            		temptempOperand2=temptempOperand2+"0";
            	else
            		temptempOperand2=temptempOperand2+"1";
            }
 
    		tempOperand2=temptempOperand2;
			
    		int tempSymbol1=0;
    		int tempSymbol2=0;
    		if(tempOperand1.charAt(0)=='1'){
    			tempSymbol1++;
    		}
    		if(tempOperand2.charAt(0)=='1'){
    			tempSymbol2++;
    		}
    		
    		
    		if(tempOperand1.length()<length){
    			int temp=length-tempOperand1.length();
    			if(tempOperand1.charAt(0)=='1'){
    				for(int n=0;n<temp;n++){
    					tempOperand1="1"+tempOperand1;
    					
    				}
    			}else{
    				for(int n=0;n<temp;n++){
    					tempOperand1="0"+tempOperand1;
    					
    				}
    			}
    		}
    		if(tempOperand2.length()<length){
    			int temp=length-tempOperand2.length();
    			if(tempOperand2.charAt(0)=='1'){
    				for(int n=0;n<temp;n++){
    					tempOperand2="1"+tempOperand2;
    					
    				}
    			}else{
    				for(int n=0;n<temp;n++){
    					tempOperand2="0"+tempOperand2;
    					
    				}
    			}
    		}
    		
            String result="";
            String temp;
            char tempC='0';
            for(int n=length;n>=4;n-=4){
            	String loopTemp=claAdder(tempOperand1.substring(n-4, n),tempOperand2.substring(n-4, n),tempC);
            	temp=loopTemp.substring(1,5);
                tempC=loopTemp.charAt(0);
                result=temp+result;
            }
            
            char isOut;
            if(tempSymbol1!=tempSymbol2){
            	isOut='0';
            }else{
            	if(tempSymbol1==1&&result.charAt(0)=='0'){
            		isOut='1';
            	}
            	else if(tempSymbol1==0&&result.charAt(0)=='1'){
            		isOut='1';
            	}else{
            		isOut='0';
            	}
            }
            
            if(tempC=='1')
            	return (isOut+"")+(symbol1+"")+result;
            else{
            	
            	String temptempResult="";
            	if(result.charAt(0)=='1'){
            	 result=adder(tempOperand2,tempString,'0',length).substring(1,length+1);
     			
                 for(int n=0;n<length;n++){
                 	if(result.charAt(n)=='1')
                 		temptempResult=temptempResult+"0";
                 	else
                 		temptempResult=temptempResult+"1";
                 }
            	}else
            		temptempResult=result;
            
            	if(symbol1=='1')
            		symbol1='0';
            	else
            		symbol1='1';
			    return (isOut+"")+(symbol1+"")+temptempResult;
            }
		}
		
		
	}
	
	/**
	 * �������ӷ����ɵ���{@link #signedAddition(String, String, int) signedAddition}�ȷ���ʵ�֡�<br/>
	 * ����floatAddition("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ļ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param gLength ����λ�ĳ���
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����ӽ�������е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatAddition (String operand1, String operand2, int eLength, int sLength, int gLength) {
		// TODO YOUR CODE HERE.
		String Ex=operand1.substring(1,eLength+1);
		String Mx=operand1.substring(eLength+1,1+eLength+sLength);
		String Ey=operand2.substring(1,eLength+1);
		String My=operand2.substring(eLength+1,1+eLength+sLength);
		char symbol1=operand1.charAt(0);
		char symbol2=operand2.charAt(0);
		char isOut='0';
		
		String tempZero="";
		for(int n=0;n<eLength;n++){
			tempZero+="0";
		}
		if(Ex.equals(tempZero)){
			Mx="0"+Mx+"000";
		}else{
			Mx="1"+Mx+"000";
		}
		if(Ey.equals(tempZero)){
			My="0"+My+"000";
		}else{
			My="1"+My+"000";
		}
		for(int n=0;n<gLength;n++){
			Mx=Mx+"0";
			My=My+"0";
		}
		String resultE;
		String tempE=integerSubtraction(Ex,Ey,eLength).substring(1,eLength+1);
		
		if(tempE.charAt(0)=='0'){
			resultE=Ex;
			Ey=Ex;
		}else{
			tempE=integerSubtraction(Ey,Ex,eLength).substring(1,eLength+1);
			
			resultE=Ey;
			Ex=Ey;
			String tempStr=Mx;
			Mx=My;
			My=tempStr;
			char tempChar=symbol1;
			symbol1=symbol2;
			symbol2=tempChar;
		}
			
		String zero="";
		for(int n=0;n<eLength;n++){
			zero=zero+"0";
		}
			
		while(!tempE.equals(zero)){
			tempE=integerSubtraction(tempE,"0001",eLength).substring(1,eLength+1);
			My="0"+My;
		}
		
		My=My.substring(0,sLength+gLength);
		Mx=Mx.substring(0,sLength+gLength);
		
		Mx=(symbol1+"")+Mx;
		My=(symbol2+"")+My;
		
		String resultM=signedAddition(Mx,My,sLength+gLength);	
		
		String resultSymbol=resultM.substring(1, 2);
		if(symbol1==symbol2){
			resultM=(resultM.charAt(0)+"")+resultM.substring(2, sLength+gLength+2);
		}else{
			resultM="0"+resultM.substring(2, sLength+gLength+2);
		}
		
		boolean isZero=false;
		String mZero="";
		for(int n=0;n<resultM.length();n++){
			mZero=mZero+"0";
		}
		if(resultM.equals(mZero)){
			isZero=true;
		}
			
		int count=0;
		for(int n=-1;n<sLength+gLength;n++){
			if(resultM.charAt(n+1)=='1'){
				count=n;
				resultM=resultM.substring(count+2, sLength+gLength+1);
				break;
			}
		}
		String countStr=Integer.toBinaryString(count);
		if(count>0){
			countStr="0"+countStr;
		}
		
		if(count!=0){
			resultE=integerSubtraction(resultE,countStr,eLength);
			if(resultE.charAt(0)=='1'){
				isOut='1';
			}
			resultE=resultE.substring(1, eLength+1);
		}
		
		int tempCount=resultM.length();
		if(tempCount<=sLength){
			for(int n=0;n<sLength-tempCount;n++){
				resultM=resultM+"0";
			}
		}else{
			resultM=resultM.substring(0,sLength);
		}
		
		
		
		if(!isZero){
		
			return (isOut+"")+resultSymbol+resultE+resultM;
		}else{
			return (isOut+"")+resultSymbol+zero.substring(0, eLength)+mZero.substring(0, sLength);
		}
	}
	
	/**
	 * �������������ɵ���{@link #floatAddition(String, String, int, int, int) floatAddition}����ʵ�֡�<br/>
	 * ����floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ļ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param gLength ����λ�ĳ���
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ�������������е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatSubtraction (String operand1, String operand2, int eLength, int sLength, int gLength) {
		// TODO YOUR CODE HERE.
		
		String result;
		String tempOperand1=operand1;
		String tempOperand2;
		boolean isZero=true;
		for(int n=0;n<operand2.length();n++){
			if(operand2.charAt(n)=='1'){
				isZero=false;
				break;
			}
		}
		if(!isZero){
		if(operand2.charAt(0)=='1'){
			tempOperand2="0"+operand2.substring(1,operand2.length());
		}else{
			tempOperand2="1"+operand2.substring(1,operand2.length());
		}
		}else{
			tempOperand2=operand2;
		}
		result=floatAddition(tempOperand1,tempOperand2,eLength,sLength,gLength);
		return result;
	}
	
	/**
	 * �������˷����ɵ���{@link #integerMultiplication(String, String, int) integerMultiplication}�ȷ���ʵ�֡�<br/>
	 * ����floatMultiplication("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ĳ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����˽��,���е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatMultiplication (String operand1, String operand2, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		String positiveZero="0";
		String negativeZero="1";
		for(int n=0;n<eLength+sLength;n++){
			positiveZero=positiveZero+"0";
			negativeZero=negativeZero+"0";
		}
		if(operand1.equals(positiveZero)||operand1.equals(negativeZero)||operand2.equals(positiveZero)||operand2.equals(negativeZero)){
			return "0"+positiveZero;
		}else{
		
		int symbol1=Integer.parseInt(operand1.substring(0,1));
		int symbol2=Integer.parseInt(operand2.substring(0,1));
		String Ex=operand1.substring(1,eLength+1);
		String Ey=operand2.substring(1,eLength+1);
		String Mx="1"+operand1.substring(eLength+1,eLength+1+sLength);
		String My="1"+operand2.substring(eLength+1,eLength+1+sLength);
		String tempStr="1";
		for(int n=0;n<eLength-2;n++){
			tempStr=tempStr+"0";
		}
		tempStr=tempStr+"1";
		
		String resultE;
		String resultM;
		int resultSymbol=symbol1^symbol2;
		char isOut='0';
		for(int n=0;n<eLength;n++){
			Ex="0"+Ex;
			Ey="0"+Ey;
		}
		
		resultE=integerAddition(Ex,Ey,eLength*2).substring(eLength+1,eLength*2+1);
		resultE=integerAddition(resultE,tempStr,eLength).substring(1,eLength+1);
		
		
		resultM=fractionalMultiplication(Mx,My,sLength+1);
		
		if(!resultM.substring(0,2).equals("01")){
			resultE=integerAddition(resultE,"0001",eLength).substring(1,eLength+1);
			resultM=resultM.substring(1,1+sLength);		
		}else{
			resultM=resultM.substring(2,2+sLength);
		}
		//����ж�
		
		
		return (isOut+"")+resultSymbol+resultE+resultM;
		}
	}
	private String fractionalMultiplication(String operand1,String operand2,int length){
		
		String result;
		String C="0";
		String P="";
		for(int n=0;n<length;n++){
			P=P+"0";
		}
		String Y=operand2;
		String tempY=operand2;
		for(int n=0;n<length;n++){
		
			if(tempY.charAt(length-1-n)=='1'){ 
				char c='0';
				String tempResult="";
				for(int m=length-1;m>=0;m--){
					String temp=fullAdder(P.charAt(m),operand1.charAt(m),c);
					c=temp.charAt(0);
					tempResult=temp.substring(1, 2)+tempResult;
				}
				tempResult=(c+"")+tempResult;
				C=tempResult.substring(0, 1);
				P=tempResult.substring(1,length+1);
			}
			Y=P.substring(length-1,length)+Y.substring(0,length-1);
			P=C+P.substring(0,length-1);
			C="0";
		}
		result=P+Y;
		return result;
	}
	/**
	 * �������������ɵ���{@link #integerDivision(String, String, int) integerDivision}�ȷ���ʵ�֡�<br/>
	 * ����floatDivision("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ĳ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����˽��,���е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatDivision (String operand1, String operand2, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		String positiveZero="0";
		String negativeZero="1";
		for(int n=0;n<eLength+sLength;n++){
			positiveZero=positiveZero+"0";
			negativeZero=negativeZero+"0";
		}
		String positiveInfinite="0";
		for(int n=0;n<eLength;n++){
			positiveInfinite=positiveInfinite+"1";
		}
		for(int n=0;n<sLength;n++){
			positiveInfinite=positiveInfinite+"0";
		}
		String negativeInfinite="1"+positiveInfinite.substring(1,1+eLength+sLength);
		if(operand2.equals(positiveZero)||operand2.equals(negativeZero)){
			if(operand1.charAt(0)=='0')
				return "0"+positiveInfinite;
			else
				return "0"+negativeInfinite;
		}
		else if(operand2.equals(positiveInfinite)||operand2.equals(negativeInfinite)){
			return "0"+positiveZero;
		}
		else if(operand1.equals(positiveZero)||operand1.equals(negativeZero)){
			return "0"+positiveZero;
		}
		else {
			int symbol1=Integer.parseInt(operand1.substring(0,1));
			int symbol2=Integer.parseInt(operand2.substring(0,1));
			String Ex=operand1.substring(1,eLength+1);
			String Ey=operand2.substring(1,eLength+1);
			String Mx="1"+operand1.substring(eLength+1,eLength+1+sLength);
			String My="1"+operand2.substring(eLength+1,eLength+1+sLength);
			
			String tempStr="0";
			for(int n=1;n<eLength;n++){
				tempStr=tempStr+"1";
			}
			String result;
			
			for(int n=0;n<eLength;n++){
				Ex="0"+Ex;
				Ey="0"+Ey;
			}
			int resultSymbol=symbol1^symbol2;
			String resultE=integerSubtraction(Ex,Ey,eLength*2).substring(eLength+1,eLength*2+1);
			resultE=integerAddition(resultE,tempStr,eLength).substring(1,eLength+1);
					
			String resultM=fractionalDivision(Mx,My,sLength+1);
			if(resultM.charAt(0)=='0'){
				resultM=resultM.substring(2, sLength)+"00";
				resultE=integerSubtraction(resultE,"0001",eLength).substring(1,eLength+1);
			}else{
				resultM=resultM.substring(1, sLength)+"0";
			}
			char isOut='0';
			
			
			
			result=(isOut+"")+resultSymbol+resultE+resultM;
			return result;
		}
		
		
	}
	private String fractionalDivision(String operand1,String operand2,int length){
		String result;
		String R=operand1;
		String Q="";
		String tempQ;
		
		
		for(int n=1;n<length;n++){
			Q=Q+"0";
		}
		
		R=integerSubtraction(R,operand2,length+3).substring(3+1,length+3+1);
		
		if(R.charAt(0)=='1'){
			tempQ="0";
			Q=Q+tempQ;
		}else{
			tempQ="1";
			Q=Q+tempQ;
		}
		
		for(int n=1;n<length;n++){
			R=R.substring(1, length)+Q.substring(0, 1);
			Q=Q.substring(1,length);
			if(tempQ.equals("0")){
				R=integerAddition(R,operand2,length+3).substring(3+1,length+3+1);
			}else{
				R=integerSubtraction(R,operand2,length+3).substring(3+1,length+3+1);
			}
			
			if(R.charAt(0)=='1'){
				tempQ="0";
				Q=Q+tempQ;
			}else{
				tempQ="1";
				Q=Q+tempQ;
			}
		}
		result=Q;
		return result;
	}
}
