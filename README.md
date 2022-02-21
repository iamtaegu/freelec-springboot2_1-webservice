[2022-02-08] 무중단 서비스를 위한 AWS 서버 구성 START 

*[EC2 서버구성]*
1. EIP할당 - 고정IP 할당
		> 탄력적IP는 EC2에 연결하지 않으면 비용이 발생하므로 주의
		
1. window > putty 접속
	1. puttygen.exe로 aws에서 받은 pem > ppk로 변환
	1. putty.exe 접속
		1. Session, Host Name 입력
		1. Connection > Auth, ppk 등록
		1. Session, Saved Sessions & 접속 
		
1. 서버 생성 후 필요한 설정들
	1. java설치, sudo yum install -y java-1.8.0-openjdk-devel.x86_64
		1. java 버전 변경, sudo /usr/sbin/alternatives --config java
		1. 미사용 java 삭제, sudo yum remove java-1.7.0-openjdk
	1. 타임존 변경, sudo rm /etc/localtime && sudo ln -s /usr/share/zoneinfo/Asia/Seoul /etc/localtime
	1. Hostname 변경, sudo vim /etc/sysconfig/network && sudo reboot
		
		
*[RDS 생성 및 설정]*

1. 데이터베이스 생성
1. RDS 파라미터 설정
	1. 타임존
	1. Character Set
	1. Max Connection
1. 로컬PC에서 RDS 접속해보기
	1. mysql, workbench
1. EC2에서 RDS 접근 확인 
	1. EC2에서 MySQL CLI 설치(참고, https://goddaehee.tistory.com/292)
		1. wget https://dev.mysql.com/get/mysql80-community-release-el7-5.noarch.rpm
		1. yum localinstall mysql80-community-release-el7-5.noarch.rpm
		1. yum install mysql-community-server
		1. sudo systemctl start mysqld, sudo systemctl status mysqld
1. mysql -u 계정 -p -h host주소

*RDS 인바운드 규칙을 ANYWHERE로 했는데, 내일 EC2/로컬PC 대상만 가능하게 수정*

[EC2 서버에 프로젝트 배포]

1. sudo yum install git
1. git init, git clone url
1. ./gradlew test 
1. 배포 스크립트 만들기, /home/ec2-user/app/step1/deploy.sh
	1. 프로젝트 최신화(git pull or git clone)
	1. 프로젝트 테스트와 빌드(Gradle, Maven)
	1. 프로젝트 실행 및 재실행(EC2서버에서) 
	
	
*프로젝트명 상이한 부분 보완 freelec-springboot2-webservice, SpringbootAWS*
*프로젝트 build 안되는 이슈 확인..*
*포털 로그인 고치고, EC2 도메인 등록하여 AWS위에서 포털 로그인 구현 

git branch
git init
git clone https://github.com/iamtaegu/freelec-springboot2-webservice.git master
git remote add master https://github.com/iamtaegu/freelec-springboot2-webservice.git
git pull master https://github.com/iamtaegu/freelec-springboot2-webservice.git
git pull https://github.com/iamtaegu/freelec-springboot2-webservice.git master
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
