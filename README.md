# nurse-shift
간호사 근무표 자동 생성 프로젝트 서버

# 실행 방법

### 초기 클론 방법
- git clone --recurse-submodules {레포 주소}

### 서브모듈 업데이트
- git submodule update --remote -- merge

### Submodule 복사를 위한 Build Gradle 설정
```
processResources.dependsOn('copySecret')

tasks.register('copySecret', Copy) {
    from './security'
    include '*.yml'
    into 'src/main/resources'
}
```

# 사용 스택
- Spring Boot 2.7.16
- Java 11

### Cloud 
- EC2
- S3

### 데이터베이스
- MySQL 8.0.33
- RDS