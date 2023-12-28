<template>
<div class="email">
    <div class="tools">
        <span class="back" @click="backButton">
            <img src="../assets/arrow.png">
        </span>
    </div>
    <div class="subject">
        {{ subject }}
    </div>
    <div class="details">
        <div class="toFrom">
            <div class="userPhoto"><img src="../assets/user.png"></div>
            <span class="send">
                <span class="from">{{from}}</span>
                <div class="to">
                    To:
                    <ul>
                        <li v-if="!showTo"> {{to[0]}} </li>
                        <li v-else v-for="email in to" :key="email" class="liHover">{{email}}</li>
                    </ul>
                    <span class="showTo" @click="showTo=!showTo">
                        <img src="../assets/arrowdown.png">
                    </span>
                    <!-- <span v-for="email in to" :key="email">{{email}}, </span> -->
                </div>
            </span>
        </div>
        <div class="timeDate">
            <span class="date">{{localDate}}</span>
            <span class="time">{{localTime}}</span>
        </div>
    </div>
    <div class="content">
        <p>
            {{ content }}
        </p>
    </div>
    <div v-if="attachments.length" class="attachment-info">
        <div v-for="(attachment, index) in attachments" :key="index" class="attachment-info-item">
            <a :href="decodeBase64(attachment.content)" :download="attachment.name" target="_blank">{{ attachment.name }}</a>
            <div class="attachment-size">({{ formatSize(attachment.size) }})</div>
        </div>
    </div>
</div>
</template>

<script>
export default {
    name: 'mail',
    props: ["from", "to", "subject", "localTime", "localDate", "content", "attachments"],
    data(){
        return {
            showTo: false,
        }
    },
    methods: {
        backButton(){
            this.$emit('back-clicked', true);
        },
        decodeBase64(base64String) {
            const byteCharacters = atob(base64String);
            const byteNumbers = new Array(byteCharacters.length);

            for (let i = 0; i < byteCharacters.length; i++) {
                byteNumbers[i] = byteCharacters.charCodeAt(i);
            }
            const byteArray = new Uint8Array(byteNumbers);
            const blob = new Blob([byteArray], { type: 'application/octet-stream' });
            return URL.createObjectURL(blob);
        },
        formatSize(sizeInBytes) {
            const kilobytes = sizeInBytes / 1024;
            if (kilobytes < 1) {
                return sizeInBytes + ' B';
            } else {
                const megabytes = kilobytes / 1024;
                if (megabytes < 1) {
                    return kilobytes.toFixed(2) + ' KB';
                } else {
                    return megabytes.toFixed(2) + ' MB';
                }
            }
        },
    }
};
</script>

<style scoped>
.email {
    text-align: left;
    padding: 20px;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    justify-content: flex-start;
    background: white;
    margin: 20px;
    border-radius: 20px;
    box-shadow: 0px 0px 20px 6px rgba(0, 0, 0, 0.1);
    max-height: 100%;
}
.tools{
    margin-bottom: 20px;
    border-bottom: 0.1px solid grey;
    width: 100%;
    padding-bottom: 10px;
    display: flex;
    justify-content: flex-start;
}
.back{
    cursor: pointer;
    border-radius: 50%;
    transition: 0.3s;
}
.back img{
    width: 40px;
}
.back:hover{
    background: #EEE;
}
.subject {
    color: black;
    font-size: 35px;
    padding-left: 60px;
    margin-bottom: 30px;
}
.details{
    display: flex;
    font-size: 15px;
    justify-content: space-between;
    width: 100%;
}
.toFrom{
    display: flex;
    flex-direction: row;
    align-items: center;
    padding: 5px;
    margin-bottom: 50px;
}
.send{
    display: flex;
    flex-direction: column;
}
.from{
    font-weight: bold;
    font-size: 20px;
    margin-bottom: 10px;
}
.to {
    font-size:14px ;
    display: flex;
    position: relative;
}
.to ul{
    padding: 0;
    margin: 0;
    list-style: none;
    position: absolute;
    left: 30px;
}
.showTo{
    position: absolute;
    left: 150px;
}
.showTo img {
    width: 25px;
    cursor: pointer;
}
.userPhoto img{
    width: 70px;    
}
.timeDate{
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    font-size: 15px;
    padding: 20px;
}
.content{
    display: flex;
    line-height: 35px;
    /* width: 100%; */
    justify-content: center;
    padding: 50px;
    margin-left: auto;
    margin-right: auto;
    padding-top: 0;
    margin-top: 0;
}
.attachment-info {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-self: center;
    width: calc(100% - 50px);
}

.attachment-info-item {
    display: flex;
    padding: 10px;
    background: #EEE;
    justify-content: space-between;
}
</style>
